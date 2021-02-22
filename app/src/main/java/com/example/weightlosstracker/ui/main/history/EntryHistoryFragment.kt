package com.example.weightlosstracker.ui.main.history

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.weightlosstracker.R
import com.example.weightlosstracker.databinding.FragmentEntryHistoryBinding
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.util.BaseFragment
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryHistoryFragment : BaseFragment<EntryHistoryViewModel,
        DataState<List<WeightEntry>>>(
    R.layout.fragment_entry_history,
    EntryHistoryViewModel::class.java
) {

    private val binding by viewBinding(FragmentEntryHistoryBinding::bind)
    private lateinit var entriesAdapter: EntriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        entriesAdapter = EntriesAdapter(requireContext())
        binding.entriesRecView.adapter = entriesAdapter
    }

    override fun updateUi(model: DataState<List<WeightEntry>>) {
        when (model) {
            is DataState.Success -> {
                entriesAdapter.submitList(model.data)
                binding.progressBar.isVisible = false
            }
            is DataState.Error -> {
                binding.progressBar.isVisible = false
            }
            DataState.Loading -> {
                binding.progressBar.isVisible = true
            }
        }
    }
}