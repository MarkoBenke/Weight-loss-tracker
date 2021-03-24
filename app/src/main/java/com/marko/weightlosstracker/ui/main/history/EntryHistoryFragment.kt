package com.marko.weightlosstracker.ui.main.history

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentEntryHistoryBinding
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.ui.core.BaseFragment
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.ui.dialogs.ConfirmationDialog
import com.marko.weightlosstracker.ui.main.MainActivity
import com.marko.weightlosstracker.ui.main.MainViewModel
import com.marko.weightlosstracker.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryHistoryFragment : BaseFragment<EntryHistoryViewModel,
        DataState<List<WeightEntry>>>(
    R.layout.fragment_entry_history,
    EntryHistoryViewModel::class.java
) {

    private val binding by viewBinding(FragmentEntryHistoryBinding::bind)
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var entriesAdapter: EntriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        entriesAdapter = EntriesAdapter(requireContext())
        binding.entriesRecView.apply {
            adapter = entriesAdapter
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }

        entriesAdapter.setItemClickListener {
            val bundle = bundleOf(
                MainActivity.WEIGHT_ENTRY_KEY to it
            )
            findNavController().navigate(R.id.action_entryHistory_to_entryDetailsFragment, bundle)
        }
    }

    override fun updateUi(model: DataState<List<WeightEntry>>) {
        when (model) {
            is DataState.Success -> {
                entriesAdapter.submitList(model.data.toMutableList())
                binding.progressBar.isVisible = false
            }
            is DataState.Error -> {
                showErrorDialog()
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

            val confirmationDialog = ConfirmationDialog.newInstance(
                getString(R.string.delete_entry_dialog_title), getString(
                    R.string.delete_entry_dialog_description
                )
            )
            confirmationDialog.setDialogClickListener(object :
                ConfirmationDialog.ConfirmationDialogClickListener {
                override fun onConfirmClicked() {
                    if (mainViewModel.isNetworkAvailable) {
                        viewModel.deleteEntry(item)
                    } else {
                        entriesAdapter.notifyDataSetChanged()
                        showNoInternetConnectionToast()
                    }
                }

                override fun onDeclineClicked() {
                    entriesAdapter.notifyDataSetChanged()
                }
            })
            confirmationDialog.show(parentFragmentManager, ConfirmationDialog.TAG)
        }
    }
}