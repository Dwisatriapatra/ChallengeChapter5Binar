package com.example.challengechapter5binar.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.challengechapter5binar.R
import com.example.challengechapter5binar.databinding.FragmentLoginBinding
import com.example.challengechapter5binar.model.GetAllUserResponseItem
import com.example.challengechapter5binar.viewmodel.ViewModelUser

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding : FragmentLoginBinding
    private lateinit var viewModel : ViewModelUser
    private lateinit var listUser : List<GetAllUserResponseItem>
    private lateinit var sharedPreference : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        //action for login button
        binding.buttonLogin.setOnClickListener {
            if(binding.loginInputEmail.text.isNotEmpty() && binding.loginInputPassword.text.isNotEmpty()){
                getDataUserUsingViewModel()
            }else{
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }

        //action for register button
        binding.loginRegisteringAccount.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    //get data user function
    private fun getDataUserUsingViewModel(){
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveUserObserver().observe(viewLifecycleOwner, Observer {
            listUser = it
            loginAuth(listUser)
        })
        viewModel.setLiveDataUserFromApi()
    }

    //login authentication function
    private fun loginAuth(userDataList : List<GetAllUserResponseItem>) {
        //make shared preference that saving log in activity history
        sharedPreference = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)

        //get all data from user input
        val inputEmail = binding.loginInputEmail.text.toString()
        val inputPassword = binding.loginInputPassword.text.toString()

        //checking email and password of user to authenticate
        for(i in userDataList.indices){
            if(inputPassword == userDataList[i].password && inputEmail == userDataList[i].email){
                Toast.makeText(requireContext(), "Berhasil login", Toast.LENGTH_SHORT).show()
                //bundling all information about user
                navigationBundling(userDataList[i])
            }
            if(i == userDataList.lastIndex && inputPassword != userDataList[i].password && inputEmail != userDataList[i].email){
                Toast.makeText(requireContext(), "Gagal login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // navigation with bundling function
    private fun navigationBundling(currentUser: GetAllUserResponseItem){
        sharedPreference = requireActivity().getSharedPreferences("LOGGED_IN", Context.MODE_PRIVATE)
        //shared pref to save log in hostory
        val sharedPref = sharedPreference.edit()
        sharedPref.putString("DATAEMAIL", currentUser.email)
        sharedPref.putString("DATAPASSWORD", currentUser.password)
        sharedPref.putString("DATANAMA", currentUser.name)
        sharedPref.putString("DATAUSERNAME", currentUser.username)
        sharedPref.putString("DATATANGGALLAHIR", currentUser.tanggalLahir)
        sharedPref.putString("DATAALAMAT", currentUser.alamat)
        sharedPref.putString("DATAIMAGE", currentUser.image)
        sharedPref.putString("DATAID", currentUser.id)
        sharedPref.apply()

        //some code to fix destination error
        /*error : java.lang.IllegalArgumentException: Navigation action/destination
        a cannot be found from the current destination Destination(id/loginFragment) label=Home
        class=com.sample.store.main.dashboard.ui.ui.home.mainui.HomeFragment*/
        val currentDestinationIsLogin = this.findNavController().currentDestination == this.findNavController().findDestination(R.id.loginFragment)
        val currentDestinationIsMainHomeFragment = this.findNavController().currentDestination == this.findNavController().findDestination(R.id.mainHomeFragment)

        if(currentDestinationIsLogin && !currentDestinationIsMainHomeFragment){
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_mainHomeFragment)
        }


    }
}