package com.example.weightlosstracker.ui.main.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.weightlosstracker.R
import com.example.weightlosstracker.databinding.FragmentEntryHistoryBinding
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryHistoryFragment : Fragment(R.layout.fragment_entry_history) {

    private val binding by viewBinding(FragmentEntryHistoryBinding::bind)
    private val viewModel: EntryHistoryViewModel by viewModels()
    private lateinit var entriesAdapter: EntriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        entriesAdapter = EntriesAdapter(requireContext())
        binding.entriesRecView.adapter = entriesAdapter
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.weightEntriesLiveData.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success -> entriesAdapter.submitList(dataState.data)
                is DataState.Error -> {
                    //TODO
                }
                DataState.Loading -> {
                    //TODO
                }
            }
        })
    }
}