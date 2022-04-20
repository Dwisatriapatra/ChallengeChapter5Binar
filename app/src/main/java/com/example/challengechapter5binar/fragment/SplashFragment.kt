package com.example.challengechapter5binar.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import com.example.challengechapter5binar.R
import com.example.challengechapter5binar.databinding.FragmentSplashBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var binding : FragmentSplashBinding
    private lateinit var sharedPreference : SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        sharedPreference = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            if(sharedPreference.contains("DATAEMAIL") && sharedPreference.contains("DATAPASSWORD")){
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_mainHomeFragment)
            }else{
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }, 3000)
    }
}