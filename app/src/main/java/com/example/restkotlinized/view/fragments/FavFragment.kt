package com.example.restkotlinized.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.restkotlinized.R

class FavFragment(context: Context) : Fragment(){
    val ctx = context
    companion object Factory {
        fun create(context: Context): FavFragment =
            FavFragment(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fav_fragment, container, false)
}