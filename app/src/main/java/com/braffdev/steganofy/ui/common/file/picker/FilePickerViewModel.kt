package com.braffdev.steganofy.ui.common.file.picker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.braffdev.steganofy.service.ExecutionService
import com.braffdev.steganofy.service.FileService

class FilePickerViewModel(val applicationContext: Context, val fileService: FileService, val executionService: ExecutionService) {

    val liveDataIntent = MutableLiveData<Intent>()
    val liveDataFileName = MutableLiveData<String>()
    val liveDataFileInfo = MutableLiveData<String>()

    lateinit var mimeType: String
    var pickerTextRes: Int = -1

    fun initialize(mimeType: String, @StringRes pickerTextRes: Int) {
        this.mimeType = mimeType
        this.pickerTextRes = pickerTextRes
    }

    fun selectFileClicked() {
        val isImage = mimeType.startsWith("image")

        val pickIntent = if (isImage && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+: Photo Picker — всегда стартует в галерее, не помнит последний путь
            Intent(MediaStore.ACTION_PICK_IMAGES)
        } else {
            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = mimeType
                if (isImage && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    putExtra(
                        DocumentsContract.EXTRA_INITIAL_URI,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                }
            }
        }

        liveDataIntent.value = Intent.createChooser(pickIntent, applicationContext.getString(pickerTextRes))
    }

    fun onFileUriChanged(uri: Uri) {
        executionService.executeInBackground {
            liveDataFileName.postValue(fileService.getFileName(uri))
            liveDataFileInfo.postValue(fileService.formatFileInfo(uri))
        }
    }
}
