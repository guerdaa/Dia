package com.android.dia.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.android.dia.R
import dagger.android.support.DaggerDialogFragment

class HelpDialogFragment : DaggerDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help_dialog, container, false)
    }

    companion object {
        fun newInstance(): HelpDialogFragment {
            return HelpDialogFragment()
        }
    }
}
