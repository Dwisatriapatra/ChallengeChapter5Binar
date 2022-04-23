package com.example.challengechapter5binar.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.challengechapter5binar.R
import com.example.challengechapter5binar.databinding.FragmentProfileBinding
import com.example.challengechapter5binar.model.GetAllUserResponseItem
import com.example.challengechapter5binar.model.RequestUser
import com.example.challengechapter5binar.networking.ApiUserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var sharedPreference : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        initField()

        //logout button action
        binding.profileButtonLogout.setOnClickListener {
            logout()
        }

        //update button action
        binding.profileButtonUpdate.setOnClickListener {
            sharedPreference = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
            val namaLengkap = binding.profileNamaLengkap.text.toString()
            val tanggalLahir = binding.profileTanggalLahir.text.toString()
            val alamat = binding.profileAlamat.text.toString()
            val username = binding.profileUsername.text.toString()
            val email = binding.profileEmail.text.toString()
            val password = binding.profilePassword.text.toString()
            val image = sharedPreference.getString("DATAIMAGE", null)
            val id = sharedPreference.getString("DATAID", null)
            updateUserData(id!!, namaLengkap, tanggalLahir, alamat, username, email, password, image!!)
        }
    }

    // initializing text of all edit text
    private fun initField(){

        sharedPreference = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        val namaLengkap = sharedPreference.getString("DATANAMA", null)
        val tanggalLahir = sharedPreference.getString("DATATANGGALLAHIR", null)
        val alamat = sharedPreference.getString("DATAALAMAT", null)
        val username = sharedPreference.getString("DATAUSERNAME", null)
        val image = sharedPreference.getString("DATAIMAGE", null)
        val email = sharedPreference.getString("DATAEMAIL", null)
        val password = sharedPreference.getString("DATAPASSWORD", null)

        binding.profileNamaLengkap.setText(namaLengkap)
        binding.profileTanggalLahir.setText(tanggalLahir)
        binding.profileAlamat.setText(alamat)
        binding.profileUsername.setText(username)
        binding.profileEmail.setText(email)
        binding.profilePassword.setText(password)
        binding.profileKonfirmasiPassword.setText(password)
        Glide.with(requireView())
            .load(image)
            .error(R.drawable.ic_launcher_background)
            .override(200, 200)
            .into(binding.profileImage)
    }

    //function for logout action
    private fun logout(){
        AlertDialog.Builder(requireContext())
            .setTitle("LOGOUT")
            .setMessage("Yakin ingin logout?")
            .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                //clear shared preference, so the user must login again to access home after logging out
                val sharedPreferences = requireContext().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
                val sf = sharedPreferences.edit()
                sf.clear()
                sf.apply()

                //reload activity
                val mIntent = activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }

    //function for updating data
    private fun updateUserData(
        id : String,
        namaLengkap : String,
        tanggalLahir : String,
        alamat : String,
        username : String,
        email : String,
        password : String,
        image : String
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle("UPDATE")
            .setMessage("Yakin ingin mengupdate data anda?")
            .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                sharedPreference = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
                ApiUserClient.instance.updateUser(id, RequestUser(
                    alamat,
                    email,
                    image,
                    namaLengkap,
                    password,
                    tanggalLahir,
                    username
                ))
                    .enqueue(object : Callback<List<GetAllUserResponseItem>> {
                        override fun onResponse(
                            call: Call<List<GetAllUserResponseItem>>,
                            response: Response<List<GetAllUserResponseItem>>
                        ) {
                            if(response.isSuccessful){
                                Toast.makeText(requireContext(), "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
                                //reload activity
                                val mIntent = activity?.intent
                                activity?.finish()
                                startActivity(mIntent)
                            }else{
                                Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                            Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                        }

                    })
            }.show()
    }
}