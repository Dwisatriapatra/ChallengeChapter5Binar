package com.example.challengechapter5binar.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.challengechapter5binar.R
import com.example.challengechapter5binar.databinding.FragmentRegisterBinding
import com.example.challengechapter5binar.model.PostNewUser
import com.example.challengechapter5binar.model.RequestUser
import com.example.challengechapter5binar.networking.ApiUserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        //register button action
        binding.buttonRegister.setOnClickListener {
            val nama = binding.registerInputNama.text.toString()
            val username = binding.registerInputUsername.text.toString()
            val alamat = binding.registerInputAlamat.text.toString()
            val tanggalLahir = binding.registerInputTanggalLahir.text.toString()
            val image = "http://placeimg.com/640/480/city"
            val password = binding.registerInputPassword.text.toString()
            val email = binding.registerInputEmail.text.toString()
            val konfirmasiPassword = binding.registerInputKonfirmasiPassword.text.toString()

            //check if all fields is not empty
            if (nama.isNotEmpty() && username.isNotEmpty() && alamat.isNotEmpty() &&
                tanggalLahir.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() &&
                konfirmasiPassword.isNotEmpty()
            ) {
                //check similarity of password and konfirmasiPassword
                if (password == konfirmasiPassword) {
                    postDataNewUser(alamat, email, image, username, tanggalLahir, password, nama)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Password dan konfirmasi password harus sama",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    //function for adding new user to REST API
    private fun postDataNewUser(
        alamat: String, email: String, image: String, username: String,
        tanggalLahir: String, password: String, name: String
    ) {
        ApiUserClient.instance.postDataUser(
            RequestUser(
                alamat,
                email,
                image,
                name,
                password,
                tanggalLahir,
                username
            )
        )
            .enqueue(object : Callback<PostNewUser> {
                override fun onResponse(call: Call<PostNewUser>, response: Response<PostNewUser>) {
                    if (response.isSuccessful) {
                        checkIfFragmentAttached {
                            Toast.makeText(
                                requireContext(),
                                "Registrasi berhasil",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Navigation.findNavController(view!!)
                                .navigate(R.id.action_registerFragment_to_loginFragment)
                        }

                    } else {
                        checkIfFragmentAttached {
                            Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                override fun onFailure(call: Call<PostNewUser>, t: Throwable) {
                    checkIfFragmentAttached {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }

    //function to fixing requireContext error problem
    fun checkIfFragmentAttached(operation: Context.() -> Unit) {
        if (isAdded && context != null) {
            operation(requireContext())
        }
    }
}