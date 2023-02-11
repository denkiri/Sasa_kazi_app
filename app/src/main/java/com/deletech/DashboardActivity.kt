package com.deletech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.deletech.sasakazi.MainActivity
import com.deletech.sasakazi.R
import com.deletech.sasakazi.storage.PreferenceManager
import com.deletech.ui.main.DashboardFragment
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DashboardFragment.newInstance())
                .commitNow()
        }
    }
    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.container)
        if (f is DashboardFragment) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit")
            builder.setMessage("Are You Sure?")

            builder.setPositiveButton("Yes") { dialog, which ->
                dialog.dismiss()
                PreferenceManager(this).setLoginStatus(0)
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }

            builder.setNegativeButton("No", { dialog, which -> dialog.dismiss() })
            val alert = builder.create()
            alert.show()            //additional code
        } else {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)

        }
    }
}