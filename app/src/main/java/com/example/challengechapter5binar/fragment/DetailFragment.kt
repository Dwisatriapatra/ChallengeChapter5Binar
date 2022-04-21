package com.example.challengechapter5binar.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.challengechapter5binar.R
import com.example.challengechapter5binar.databinding.FragmentDetailBinding
import com.example.challengechapter5binar.model.GetAllGhibliFilmsResponseItem

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var binding : FragmentDetailBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        getAllDetails()
    }

    //get all data that parsed from mainHomeFragment
    private fun getAllDetails() {
        val details = arguments?.getParcelable<GetAllGhibliFilmsResponseItem>("GHIBLIFILMDATA")
        val judulInggris = details!!.title
        val judulAsli = details.originalTitle
        val judulRomaji = details.originalTitleRomanised
        val director = details.director
        val releaseDate = details.releaseDate
        val producer = details.producer
        val deskripsi = details.description
        val image = details.image

        //set text of view
        binding.detailJudulAsli.text = judulAsli
        binding.detailJudulInggris.text = judulInggris
        binding.detailJudulRomaji.text = judulRomaji
        binding.detailDirector.text = director
        binding.detailProducer.text = producer
        binding.detailTanggalRilis.text = releaseDate
        binding.detailDeskripsi.text = deskripsi
        Glide.with(requireContext())
            .load(image)
            .error(R.drawable.ic_launcher_background)
            .override(50, 100)
            .into(binding.detailImage)
    }
}