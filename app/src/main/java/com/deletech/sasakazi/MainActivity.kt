package com.deletech.sasakazi
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.deletech.DashboardActivity
import com.deletech.sasakazi.auth.RegisterActivity
import com.deletech.sasakazi.storage.PreferenceManager
class MainActivity : AppCompatActivity() {
    private val time:Long=4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({
            init()
        }, time)

    }
    fun  init() {
        if(PreferenceManager(this).getLoginStatus()==0) {
            startActivity(Intent(this@MainActivity,RegisterActivity::class.java))
            finish()
        } else{
            startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
            finish()
        }
    }

}