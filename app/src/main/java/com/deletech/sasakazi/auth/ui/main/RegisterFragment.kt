package com.deletech.sasakazi.auth.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.deletech.sasakazi.R
import com.deletech.sasakazi.custom.Resource
import com.deletech.sasakazi.custom.Status
import com.deletech.sasakazi.models.auth.Auth
import com.deletech.sasakazi.models.auth.Profile
import com.deletech.sasakazi.utils.Validator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_register.*
class RegisterFragment : Fragment() {
    companion object {
        fun newInstance() = RegisterFragment()
    }
    private lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        back.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment())
                .commitNow()
        }
        register.setOnClickListener {
            signUp()
        }
        viewModel.observeSignUp().observe(viewLifecycleOwner) { data ->
            run {
                setStatus(data)
                if (data.status == Status.SUCCESS && data.data != null) {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, LoginFragment())
                        .commitNow()
                }
            }
        }

    }

    private fun setStatus(data: Resource<Auth>) {
        val status: Status = data.status
        if (status == Status.LOADING) {
            avi.visibility = View.VISIBLE
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else if (status == Status.ERROR || status == Status.SUCCESS) {
            avi.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        if (status == Status.ERROR) {
            if (data.message != null) {
                view?.let { Snackbar.make(it, data.message.toString(), Snackbar.LENGTH_LONG).show() }
            }
        }
    }
    private fun validate(): Boolean {
        if (!Validator.isValidName(name)) {
            return false
        }
        if (!Validator.isValidPhone(phone)) {
            return false
        }
        if (!Validator.isValidEmail(email)) {
            return false
        }
        if (!Validator.isValidPassword(password)) {
            return false
        }
        return true
    }
    private fun getParameters(): Auth {
        return Auth(Profile(email.text.toString(), phone.text.toString(), name.text.toString(),password.text.toString()))
    }
    fun signUp(){
        if (validate()) {
            viewModel.signUp(getParameters())
        }


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

}