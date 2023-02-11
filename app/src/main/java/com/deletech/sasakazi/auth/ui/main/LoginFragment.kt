package com.deletech.sasakazi.auth.ui.main
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.deletech.DashboardActivity
import com.deletech.sasakazi.R
import com.deletech.sasakazi.custom.Resource
import com.deletech.sasakazi.custom.Status
import com.deletech.sasakazi.models.auth.Auth
import com.deletech.sasakazi.models.auth.Profile
import com.deletech.sasakazi.utils.Validator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
class LoginFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        login.setOnClickListener {
            signIn()
        }
        viewModel.observeSignIn().observe(viewLifecycleOwner) { data ->
            run {
                setStatus(data)
                if (data.status == Status.SUCCESS && data.data != null) {
                    viewModel.saveProfile(data.data)
                    startActivity(Intent(activity, DashboardActivity::class.java))
                    activity?.finish()
                }
            }
        }
        create_account.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, RegisterFragment())
            .commitNow() }

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
        if (!Validator.isValidEmail(email)) {
            return false
        }
        if (!Validator.isValidPassword(password)) {
            return false
        }

        return true
    }
    private fun getParameters(): Auth {

        return Auth(Profile(email.text.toString(), password.text.toString()))
    }
    fun signIn(){
        if (validate()) {
            viewModel.signIn(getParameters())
        }


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}