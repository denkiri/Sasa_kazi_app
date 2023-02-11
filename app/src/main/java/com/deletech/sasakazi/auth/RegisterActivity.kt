package com.deletech.sasakazi.auth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deletech.sasakazi.auth.ui.main.LoginFragment
import com.deletech.sasakazi.auth.ui.main.RegisterFragment
import com.deletech.sasakazi.R
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }
}