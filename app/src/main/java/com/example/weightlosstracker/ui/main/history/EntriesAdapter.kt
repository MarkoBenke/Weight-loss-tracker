package com.example.weightlosstracker.ui.main.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weightlosstracker.R
import com.example.weightlosstracker.databinding.EntryListItemBinding
import com.example.weightlosstracker.domain.WeightEntry

class EntriesAdapter(private val context: Context) :
    ListAdapter<WeightEntry, EntriesAdapter.EntriesViewHolder>(EntriesDiffCallback()) {

    inner class EntriesViewHolder(val binding: EntryListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntriesViewHolder {
        return EntriesViewHolder(
            EntryListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: EntriesViewHolder, position: Int) {
        val weightEntry = getItem(position)

        holder.binding.currentWeight.text =
            context.getString(R.string.kg, weightEntry.currentWeight.toString())
        holder.binding.date.text = weightEntry.date

        if (position == itemCount - 1) {
            holder.binding.weightDifference.isVisible = false
            holder.binding.flagIcon.isVisible = true
        } else {
            holder.binding.weightDifference.isVisible = true
            holder.binding.flagIcon.isVisible = false
        }

        if (itemCount > position + 1) {
            val nextEntry = getItem(position + 1)

            val weightDiff = weightEntry.currentWeight - nextEntry.currentWeight
            holder.binding.weightDifference.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (weightDiff < 0) R.color.positiveTextColor else R.color.negativeTextColor
                )
            )
            holder.binding.weightDifference.text =
                context.getString(R.string.kg, weightDiff.toString())
        }

        if (weightEntry.waistSize != 0) {
            holder.binding.waistSize.isVisible = true
            holder.binding.waistSize.text =
                context.getString(R.string.cm_decimal, weightEntry.waistSize)
        } else {
            holder.binding.waistSize.isVisible = false
        }

        if (weightEntry.description.isNotEmpty()) {
            holder.binding.description.isVisible = true
            holder.binding.description.text = weightEntry.description
        } else {
            holder.binding.description.isVisible = false
        }
    }
}

class EntriesDiffCallback : DiffUtil.ItemCallback<WeightEntry>() {
    override fun areItemsTheSame(oldItem: WeightEntry, newItem: WeightEntry): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: WeightEntry, newItem: WeightEntry): Boolean {
        return oldItem == newItem
    }
}