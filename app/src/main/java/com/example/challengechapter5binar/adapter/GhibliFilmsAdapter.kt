package com.example.challengechapter5binar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapter5binar.R
import com.example.challengechapter5binar.databinding.ItemAdapterGhibliFilmBinding
import com.example.challengechapter5binar.model.GetAllGhibliFilmsResponseItem

class GhibliFilmsAdapter(
    private val onClick: (GetAllGhibliFilmsResponseItem) -> Unit
) : RecyclerView.Adapter<GhibliFilmsAdapter.ViewHolder>() {
    // initializing list dan make function to set value of that list
    private var listGhibliFilms : List<GetAllGhibliFilmsResponseItem>? = null
    fun setDataGhibliFilms(list : List<GetAllGhibliFilmsResponseItem>){
        this.listGhibliFilms = list
    }

    //view holder with viewBinding
    inner class ViewHolder(val binding: ItemAdapterGhibliFilmBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GhibliFilmsAdapter.ViewHolder {
        val binding = ItemAdapterGhibliFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //set all text in view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GhibliFilmsAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(listGhibliFilms!![position]) {
                binding.ghibliFilmsTitle.text = "Title : $originalTitleRomanised / " +
                        "$title ($originalTitle)"
                binding.ghibliFilmsProducer.text = "Producer : $producer"
                binding.ghibliFilmsDirector.text = "Director : $director"
                binding.ghibliFilmsReleaseData.text = "Release date : $releaseDate"
                Glide.with(binding.ghibliFilmsImage.context)
                    .load(image)
                    .error(R.drawable.ic_launcher_background)
                    .override(50, 100)
                    .into(binding.ghibliFilmsImage)
            }
        }
        holder.binding.seeDetailButton.setOnClickListener {
            onClick(listGhibliFilms!![position])
        }

    }

    override fun getItemCount(): Int {
        return if(listGhibliFilms.isNullOrEmpty()){
            0
        }else{
            listGhibliFilms!!.size
        }
    }
}