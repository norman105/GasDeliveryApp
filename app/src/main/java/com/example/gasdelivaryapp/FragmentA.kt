package com.example.gasdelivaryapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.activity_onboardin_screen.*


class FragmentA : Fragment() {
    lateinit var Skip1: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_a,framelayout, false)
        Skip1 =view.findViewById(R.id.Skip1)

        Skip1.setOnClickListener {
            val intent = Intent(activity,Login::class.java)
            activity?.startActivity(intent)
        }

        return view
    }
}

