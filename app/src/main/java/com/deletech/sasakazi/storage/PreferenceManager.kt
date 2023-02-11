package com.deletech.sasakazi.storage
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
class PreferenceManager (internal var _context:Context){
    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor
    internal var PRIVATE_MODE = 0
    companion object {
        private val LOGIN_STATUS = "LOGIN_STATUS"
        private val PREF_NAME = "sasaKazi_preferences"
    }
    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
    fun clearUser() {
        editor.clear()
        editor.commit()
    }
    fun setLoginStatus(status:Int){
        editor.putInt(LOGIN_STATUS,status)
        editor.commit()
    }
    fun getLoginStatus():Int {
        return  pref.getInt(LOGIN_STATUS,0)

    }
}