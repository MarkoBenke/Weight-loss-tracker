package com.marko.weightlosstracker.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.ConfirmationDialogBinding
import com.marko.weightlosstracker.ui.core.viewBinding

class ConfirmationDialog : DialogFragment() {

    private val binding by viewBinding(ConfirmationDialogBinding::bind)
    private var listener: ConfirmationDialogClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirmation_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListeners()
    }

    private fun initViews() {
        isCancelable = false
        val title = requireArguments().getString(DIALOG_TITLE)
        val description = requireArguments().getString(DIALOG_DESCRIPTION)
        with(binding) {
            dialogTitle.text = title
            dialogDescription.text = description
        }
    }

    private fun setListeners() {
        binding.confirm.setOnClickListener {
            listener?.onConfirmClicked()
            dismiss()
        }

        binding.cancel.setOnClickListener {
            listener?.onDeclineClicked()
            dismiss()
        }
    }

    fun setDialogClickListener(listener: ConfirmationDialogClickListener) {
        this.listener = listener
    }

    interface ConfirmationDialogClickListener {
        fun onConfirmClicked()
        fun onDeclineClicked()
    }

    companion object {
        const val TAG = "ConfirmationDialog"
        private const val DIALOG_TITLE = "title"
        private const val DIALOG_DESCRIPTION = "desc"

        fun newInstance(dialogTitle: String, dialogDescription: String) =
            ConfirmationDialog().apply {
                arguments = Bundle().apply {
                    putString(DIALOG_TITLE, dialogTitle)
                    putString(DIALOG_DESCRIPTION, dialogDescription)
                }
            }
    }
}