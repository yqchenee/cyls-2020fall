package com.example.cyls_2020fall.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.cyls_2020fall.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button_theme_color = view.findViewById<android.widget.Button>(R.id.button_theme_colors)
        val options = androidx.navigation.navOptions {
            anim {
                enter = R.anim.slide
            }
        }
        button_theme_color?.setOnClickListener {
            view.findNavController().navigate(R.id.themeColorsFragment, null, options)
        }
    }
}