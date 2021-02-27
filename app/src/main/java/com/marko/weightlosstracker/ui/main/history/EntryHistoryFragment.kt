package com.marko.weightlosstracker.ui.main.history

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentEntryHistoryBinding
import com.marko.weightlosstracker.domain.WeightEntry
import com.marko.weightlosstracker.util.BaseFragment
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.viewBinding
import com.google.android.material.snackbar.Snackbar
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
        binding.entriesRecView.apply {
            adapter = entriesAdapter
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }

    override fun updateUi(model: DataState<List<WeightEntry>>) {
        when (model) {
            is DataState.Success -> {
                entriesAdapter.submitList(model.data.toMutableList())
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

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val position = viewHolder.adapterPosition
            val dragFlags = 0
            val swipeFlags = createSwipeFlags(position)

            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        }

        private fun createSwipeFlags(position: Int): Int {
            return if (position == entriesAdapter.itemCount - 1) 0
            else ItemTouchHelper.START or ItemTouchHelper.END
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val item = entriesAdapter.getItem(position)
            viewModel.deleteEntry(item)
            Snackbar.make(
                requireView(),
                getString(R.string.successfully_deleted_entry),
                Snackbar.LENGTH_LONG
            ).apply {
                setAction(getString(R.string.undo)) {
                    viewModel.reverseDeletion(item)
                }
                show()
            }
        }
    }
}