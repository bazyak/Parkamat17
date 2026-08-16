package com.braffdev.steganofy.ui.reveal.success

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.braffdev.steganofy.R
import com.braffdev.steganofy.databinding.RevealSuccessFileFragmentBinding
import com.braffdev.steganofy.ui.common.file.saver.FileSavedListener
import com.braffdev.steganofy.ui.common.file.saver.FileSaverFragment
import org.koin.android.scope.AndroidScopeComponent


class RevealSuccessFileFragment : Fragment() {

    companion object {
        fun newInstance() = RevealSuccessFileFragment()
    }

    private lateinit var binding: RevealSuccessFileFragmentBinding
    private val viewModel: RevealSuccessFileViewModel by lazy { (requireActivity() as AndroidScopeComponent).scope.get() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = RevealSuccessFileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize()
        viewModel.liveDataFileInfoText.observe(viewLifecycleOwner) { binding.textViewFileInfo.text = it }
        viewModel.liveDataImagePreview.observe(viewLifecycleOwner) {
            binding.imageViewPreview.visibility = View.VISIBLE
            binding.imageViewPreview.setImageBitmap(it)
        }

        val fragment = FileSaverFragment.newInstance(viewModel.getTemporaryFileUri(), R.string.file_save_image, viewModel.mimeType)
        fragment.listener = object : FileSavedListener {
            override fun onFileSaved(uri: Uri) {
                Toast.makeText(requireContext(), R.string.file_saved, Toast.LENGTH_LONG).show()
                requireActivity().finish()
            }
        }

        childFragmentManager.beginTransaction().replace(R.id.containerSaveFile, fragment).commit()
    }
}
