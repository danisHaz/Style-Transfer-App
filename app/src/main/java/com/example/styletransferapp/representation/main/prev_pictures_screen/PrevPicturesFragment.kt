package com.example.styletransferapp.representation.main.prev_pictures_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.styletransferapp.R
import com.example.styletransferapp.representation.main.BaseFragment

class PrevPicturesFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_prev_pictures, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}