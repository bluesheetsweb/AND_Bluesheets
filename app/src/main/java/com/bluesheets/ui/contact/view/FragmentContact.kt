package com.bluesheets.ui.contact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bluesheets.databinding.FragmentContactBinding
import com.bluesheets.databinding.FragmentContactsBinding

class FragmentContact: Fragment() {

    private var binding: FragmentContactsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding?.root
    }
}