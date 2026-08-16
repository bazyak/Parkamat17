package com.braffdev.steganofy.ui.hide.wizard.settings

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.braffdev.steganofy.R
import com.braffdev.steganofy.databinding.HideWizardSettingsFragmentBinding
import com.braffdev.steganofy.ui.common.TextWatcherAdapter
import com.braffdev.steganofy.ui.common.VisibilityUtils
import com.braffdev.steganofy.ui.common.message.MessageFragment
import org.koin.android.scope.AndroidScopeComponent

class HideWizardSettingsFragment : Fragment() {

    companion object {
        fun newInstance() = HideWizardSettingsFragment()
    }

    private lateinit var binding: HideWizardSettingsFragmentBinding
    private val viewModel: HideWizardSettingsViewModel by lazy { (requireActivity() as AndroidScopeComponent).scope.get() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = HideWizardSettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataStart.observe(viewLifecycleOwner) { start(it) }
        viewModel.liveDataStartButtonEnabled.observe(viewLifecycleOwner) { binding.buttonStart.isEnabled = it }
        viewModel.liveDataEncryptionPasswordShown.observe(viewLifecycleOwner) {
            binding.editTextEncryptionPasswordLayout.visibility = VisibilityUtils.toVisibility(it)
        }
        viewModel.liveDataShowFileTooSmall.observe(viewLifecycleOwner) {
            if (it) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.containerMessage, MessageFragment.createErrorMessage(R.string.hide_image_too_small))
                    .commit()
            } else {
                childFragmentManager.findFragmentById(R.id.containerMessage)
                    ?.let { fragment -> childFragmentManager.beginTransaction().remove(fragment).commit() }
            }
        }

        binding.previous.setOnClickListener { viewModel.previousClicked() }
        binding.buttonStart.setOnClickListener { viewModel.start() }
        binding.radioGroupEncryption.setOnCheckedChangeListener { _, checkedId -> viewModel.radioButtonChecked(checkedId) }
        binding.editTextEncryptionPassword.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                viewModel.encryptionPasswordChanged(s.toString().toCharArray())
            }
        })
    }

    private fun start(intent: Intent) {
        startActivity(intent)
        requireActivity().finish()
    }
}
