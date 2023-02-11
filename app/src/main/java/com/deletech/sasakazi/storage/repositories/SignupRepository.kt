package com.deletech.sasakazi.storage.repositories
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.deletech.sasakazi.R
import com.deletech.sasakazi.custom.Resource
import com.deletech.sasakazi.models.auth.Auth
import com.deletech.sasakazi.network.NetworkUtils
import com.deletech.sasakazi.network.RequestService
import com.deletech.sasakazi.storage.PreferenceManager
import com.deletech.sasakazi.storage.SasaKaziDatabase
import com.deletech.sasakazi.storage.daos.ProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class SignupRepository(application: Application) {
    val signUPObservable = MutableLiveData<Resource<Auth>>()
    private val profileDao: ProfileDao
    private val db: SasaKaziDatabase
    private val preferenceManager: PreferenceManager = PreferenceManager(application)
    private val context: Context
    init {
        db =SasaKaziDatabase.getDatabase(application)!!
        profileDao=db.profileDao()
        context =application.applicationContext
    }
    fun signUp(parameters:Auth){
        setIsLoading()
        if (NetworkUtils.isConnected(context)){
            executeSignUp(parameters)
        }
        else{
            setIsError(context.getString(R.string.no_connection))
        }
    }
    private fun executeSignUp(parameters:Auth){
        GlobalScope.launch(context=Dispatchers.Main){
            val call= RequestService.getService("").signUp(parameters.profile?.email,parameters.profile?.mobile,parameters.profile?.name,parameters.profile?.password)
            call.enqueue(object: retrofit2.Callback<Auth> {
                override fun onFailure(call:retrofit2. Call<Auth>?,t:Throwable?){
                    setIsError(t.toString())
                }
                override fun onResponse(call: retrofit2.Call<Auth>?, response: retrofit2.Response<Auth>?) {
                    if(response!=null){
                        if (response.isSuccessful){
                            if (response.body()?.status ==1 &&!response.body()?.error!!){
                                setIsSuccessful(response.body()!!)
                            }
                            else{
                                response.body()?.message?.let{setIsError(it)}
                            }
                        }
                        else{
                            setIsError(response.toString())
                        }
                    }
                    else{
                        setIsError("Error Loggin In")
                    }
                }
            })

        }
    }
    private fun setIsLoading(){
        signUPObservable.postValue(Resource.loading(null))
    }
    private fun setIsSuccessful(parameters: Auth){
        signUPObservable.postValue(Resource.success(parameters))
    }
    private fun setIsError(message: String){
        signUPObservable.postValue(Resource.error(message, null))
    }
    fun getToken(): String {
        return profileDao.fetch().token.toString()
    }
}