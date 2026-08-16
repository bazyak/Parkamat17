package com.braffdev.steganofy.ui.common.file.saver

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.braffdev.steganofy.databinding.FileSaverFragmentBinding
import org.koin.android.ext.android.inject

class FileSaverFragment : Fragment() {

    private lateinit var binding: FileSaverFragmentBinding
    private val viewModel: FileSaverViewModel by inject()
    lateinit var listener: FileSavedListener

    private val saveFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data?.data != null) {
            val uri = result.data!!.data!!
            viewModel.onFileUriChanged(uri)
            listener.onFileSaved(uri)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            viewModel.saveFileClicked()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FileSaverFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize(getTemporaryFileUri(), getMimeType(), getButtonTextRes())
        viewModel.liveDataIntentForResult.observe(this) { saveFileLauncher.launch(it) }
        viewModel.liveDataIntent.observe(this) { startActivity(it) }

        binding.buttonShareFile.setOnClickListener { viewModel.shareFileClicked() }
        binding.buttonSaveFile.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
            } else {
                viewModel.saveFileClicked()
            }
        }

        binding.buttonSaveFile.setText(getButtonTextRes())
    }

    private fun getTemporaryFileUri(): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(EXTRA_TEMPORARY_FILE_URI, Uri::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable(EXTRA_TEMPORARY_FILE_URI)!!
        }
    }

    private fun getButtonTextRes(): Int {
        return requireArguments().getInt(EXTRA_TEXT_RES)
    }

    private fun getMimeType(): String {
        return requireArguments().getString(EXTRA_MIME_TYPE)!!
    }

    companion object {
        private const val EXTRA_TEMPORARY_FILE_URI = "tmpFileUri"
        private const val EXTRA_MIME_TYPE = "mimeType"
        private const val EXTRA_TEXT_RES = "textRes"

        fun newInstance(temporaryFileUri: Uri, @StringRes textRes: Int, mimeType: String): FileSaverFragment {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_TEMPORARY_FILE_URI, temporaryFileUri)
            bundle.putInt(EXTRA_TEXT_RES, textRes)
            bundle.putString(EXTRA_MIME_TYPE, mimeType)

            val fragment = FileSaverFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}
