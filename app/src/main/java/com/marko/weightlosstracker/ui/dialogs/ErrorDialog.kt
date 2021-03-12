package com.marko.weightlosstracker.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.ErrorDialogBinding
import com.marko.weightlosstracker.ui.core.viewBinding

class ErrorDialog : DialogFragment() {

    private val binding by viewBinding(ErrorDialogBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.error_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListeners()
    }

    private fun initViews() {
        val title = requireArguments().getString(DIALOG_TITLE)
        with(binding) {
            dialogTitle.text = title
        }
    }

    private fun setListeners() {
        binding.okay.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "ConfirmationDialog"
        private const val DIALOG_TITLE = "title"

        fun newInstance(dialogTitle: String) =
            ErrorDialog().apply {
                arguments = Bundle().apply {
                    putString(DIALOG_TITLE, dialogTitle)
                }
            }
    }
}