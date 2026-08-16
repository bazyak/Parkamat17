package com.braffdev.steganofy.ui.hide

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.braffdev.steganofy.lib.service.HideService
import com.braffdev.steganofy.lib.service.RevealService
import com.braffdev.steganofy.service.BitmapService
import com.braffdev.steganofy.service.ExecutionService
import com.braffdev.steganofy.service.FileService
import com.braffdev.steganofy.ui.common.OperationStatistics
import com.braffdev.steganofy.ui.common.OperationStatus
import com.braffdev.steganofy.ui.common.SingleLiveEvent
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class HideViewModel(
    val hideService: HideService,
    val revealService: RevealService,
    val hideDataBridge: HideDataBridge,
    val bitmapService: BitmapService,
    val fileService: FileService,
    val executionService: ExecutionService,
) {

    val liveDataStatus = MutableLiveData(OperationStatus.RUNNING)
    val liveEventCancelWarning = SingleLiveEvent<Void>()
    var temporaryOutputFileUri: Uri? = null
    var statistics: OperationStatistics? = null
    var dataSizeInBytes: Long = 0

    fun initialize() {
        val bitmap = hideDataBridge.bitmap
        require(bitmap != null) { "Bitmap must not be null" }

        val steganoData = hideDataBridge.steganoData
        require(steganoData != null) { "SteganoData must not be null" }

        hideDataBridge.clear()

        executionService.executeInBackground {
            try {
                val startTime = System.currentTimeMillis()
                val inputStream = ByteArrayInputStream(bitmapService.getPixelBytes(bitmap))
                val outputStream = ByteArrayOutputStream()

                // 1) Прячем данные в пиксели
                val startTimeHide = System.currentTimeMillis()
                inputStream.use { outputStream.use { hideService.hide(inputStream, outputStream, steganoData) } }
                val hideTimeInMs = System.currentTimeMillis() - startTimeHide

                // 2) Собираем PNG — ИМЕННО ЗДЕСЬ premultiplied alpha может убить младшие биты
                val encodedBytes = outputStream.toByteArray()
                val outputBitmap = bitmapService.createBitmapFromPixelBytes(encodedBytes, bitmap.width, bitmap.height)
                val output = bitmapService.compressToPNG(outputBitmap)

                // 3) Декодируем готовый PNG обратно ровно так же, как это сделает экран
                //    расшифровки, и достаём из него данные тем же RevealService
                val verifyBitmap = BitmapFactory.decodeByteArray(output, 0, output.size)
                val verifyPixels = bitmapService.getPixelBytes(verifyBitmap)
                val revealed = runCatching {
                    revealService.reveal(
                        ByteArrayInputStream(verifyPixels),
                        steganoData.encryptionPassword
                    )
                }.getOrNull()

                // 4) Сравниваем с тем, что ввёл пользователь
                if (revealed == null ||
                    !revealed.payload.getBytes().contentEquals(steganoData.payload.getBytes())) {
                    // 5) Не совпало — картинка не подходит, файл не сохраняем
                    verifyBitmap?.recycle()
                    outputBitmap.recycle()
                    liveDataStatus.postValue(OperationStatus.IMAGE_TOO_SIMPLE)
                    return@executeInBackground
                }

                // Совпало — сохраняем и сообщаем об успехе
                verifyBitmap?.recycle()
                temporaryOutputFileUri = fileService.createTemporaryFile("image/png", output)

                statistics = OperationStatistics(System.currentTimeMillis() - startTime, hideTimeInMs)
                dataSizeInBytes = steganoData.getEstimatedLengthInBytes().toLong()
                liveDataStatus.postValue(OperationStatus.SUCCESS)

            } catch (e: Exception) {
                Log.e("HideViewModel", e.message, e)
                liveDataStatus.postValue(OperationStatus.ERROR)
            }
        }
    }

    fun finish() {
        if (temporaryOutputFileUri != null) {
            fileService.deleteTemporaryFile(temporaryOutputFileUri!!)
        }
    }

    fun backPressed() {
        if (liveDataStatus.value == OperationStatus.RUNNING || liveDataStatus.value == OperationStatus.SUCCESS) {
            liveEventCancelWarning.send()
        }
    }
}
