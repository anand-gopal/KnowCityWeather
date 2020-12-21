package com.ana.knowcityweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val back: View? = view.findViewById<View>(R.id.back)
        back?.setOnClickListener{ findNavController(this).navigateUp() }
        init(view)
    }

    abstract fun init(view: View?)

    abstract fun getResId(): Int
}