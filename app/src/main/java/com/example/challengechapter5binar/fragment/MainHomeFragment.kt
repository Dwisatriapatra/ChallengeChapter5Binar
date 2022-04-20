package com.example.challengechapter5binar.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.challengechapter5binar.R
import com.example.challengechapter5binar.adapter.GhibliFilmsAdapter
import com.example.challengechapter5binar.databinding.FragmentMainHomeBinding
import com.example.challengechapter5binar.viewmodel.ViewModelGhibliFilm

class MainHomeFragment : Fragment(R.layout.fragment_main_home) {
    private lateinit var binding : FragmentMainHomeBinding
    private lateinit var adapterGhibliFilm : GhibliFilmsAdapter
    private lateinit var viewModel : ViewModelGhibliFilm
    private lateinit var sharedPreference : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainHomeBinding.bind(view)

        getUsername()
        initRecyclerView()
        getDataGhibliFilmUsingViewModel()
        binding.profileIcon.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainHomeFragment_to_profileFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getUsername(){
        sharedPreference = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        val username = sharedPreference.getString("DATAUSERNAME", null)
        val image = sharedPreference.getString("DATAIMAGE", null)
        binding.textWelcome.text = "Welcome, $username"
        Glide.with(requireView())
            .load(image)
            .error(R.drawable.ic_launcher_background)
            .override(50, 50)
            .into(binding.profileIcon)
    }

    private fun initRecyclerView(){
        binding.rvGhibliFilm.layoutManager = LinearLayoutManager(requireContext())
        adapterGhibliFilm = GhibliFilmsAdapter {
            val clickedGhibliData = bundleOf("GHIBLIFILMDATA" to it)
            Navigation
                .findNavController(requireView())
                .navigate(R.id.action_mainHomeFragment_to_detailFragment, clickedGhibliData)
        }
        binding.rvGhibliFilm.adapter = adapterGhibliFilm
    }

    private fun getDataGhibliFilmUsingViewModel() {
        viewModel = ViewModelProvider(this).get(ViewModelGhibliFilm::class.java)
        viewModel.getLiveGhibliFilmObserver().observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                adapterGhibliFilm.setDataGhibliFilms(it)
                adapterGhibliFilm.notifyDataSetChanged()
            }
        })
        viewModel.makeApiGhibliFilm()
    }
}