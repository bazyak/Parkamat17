package com.braffdev.steganofy.ui.reveal.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.braffdev.steganofy.R
import com.braffdev.steganofy.databinding.RevealSuccessFragmentBinding
import com.braffdev.steganofy.lib.domain.Type
import org.koin.android.scope.AndroidScopeComponent


class RevealSuccessFragment : Fragment() {

    companion object {
        fun newInstance() = RevealSuccessFragment()
    }

    private lateinit var binding: RevealSuccessFragmentBinding
    private val viewModel: RevealSuccessViewModel by lazy { (requireActivity() as AndroidScopeComponent).scope.get() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = RevealSuccessFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize()
        viewModel.liveDataStatisticsBytes.observe(viewLifecycleOwner) { binding.textViewStatisticsBytes.text = it }
        viewModel.liveDataStatisticsRevealTime.observe(viewLifecycleOwner) { binding.textViewStatisticsRevealTime.text = it }
        viewModel.liveDataStatisticsImageProcessing.observe(viewLifecycleOwner) { binding.textViewStatisticsImageProcessing.text = it }
        viewModel.liveDataStatisticsTotalTime.observe(viewLifecycleOwner) { binding.textViewStatisticsTotalTime.text = it }

        val fragment = if (viewModel.getPayloadType() == Type.FILE) {
            RevealSuccessFileFragment.newInstance()
        } else {
            RevealSuccessPlainTextFragment.newInstance()
        }

        childFragmentManager.beginTransaction().replace(R.id.containerResult, fragment).commit()
    }
}
