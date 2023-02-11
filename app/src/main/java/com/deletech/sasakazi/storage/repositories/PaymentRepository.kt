package com.deletech.sasakazi.storage.repositories
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.deletech.sasakazi.R
import com.deletech.sasakazi.custom.Resource
import com.deletech.sasakazi.models.payment.PaymentData
import com.deletech.sasakazi.network.NetworkUtils
import com.deletech.sasakazi.network.RequestService
import com.deletech.sasakazi.storage.PreferenceManager
import com.deletech.sasakazi.storage.SasaKaziDatabase
import com.deletech.sasakazi.storage.daos.ProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class PaymentRepository (application: Application) {
    private val context: Context
    private val profileDao: ProfileDao
    private val db: SasaKaziDatabase
    private val preferenceManager: PreferenceManager = PreferenceManager(application)
    val paymentObservable = MutableLiveData<Resource<PaymentData>>()
    init {
        db =SasaKaziDatabase.getDatabase(application)!!
        profileDao = db.profileDao()
        context =application.applicationContext
    }
    fun payments(user_id: String) {
        setIsLoading()
        if (NetworkUtils.isConnected(context)) {
            executePayments(user_id)
        } else {
            setIsError(context.getString(R.string.no_connection))
        }
    }
    fun pay(invoice: String,user: String?,amount: String?,phone: String?) {
        setIsLoading()
        if (NetworkUtils.isConnected(context)) {
            executePay(invoice, user, amount, phone)
        } else {
            setIsError(context.getString(R.string.no_connection))
        }
    }
    fun completePay(invoice: String,user: String?,amount: String?,phone: String?) {
        setIsLoading()
        if (NetworkUtils.isConnected(context)) {
            executeCompletePayment(invoice, user, amount, phone)
        } else {
            setIsError(context.getString(R.string.no_connection))
        }
    }
    fun pendingPayment(user_id: String) {
        setIsLoading()

        if (NetworkUtils.isConnected(context)) {
            executePendingPayment(user_id)
        } else {
            setIsError(context.getString(R.string.no_connection))
        }
    }

    private fun executePendingPayment(user_id: String) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(getToken()).pendingPayment(user_id)
            call.enqueue(object : Callback<PaymentData> {
                override fun onFailure(call: Call<PaymentData>?, t: Throwable?) {
                    setIsError(t.toString())
                }
                override fun onResponse(call: Call<PaymentData>?, response: Response<PaymentData>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == 1) {

                                setIsSuccessful(response.body()!!)
                            } else {
                                response.body()?.message?.let { setIsError(it) }
                            }
                        } else {
                            setIsError("Error Loading Data")
                        }
                    } else {
                        setIsError("Error Loading Data")
                    }
                }
            })
        }
    }
    private fun executeCompletePayment(user_id: String) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(getToken()).completePayment(user_id)
            call.enqueue(object : Callback<PaymentData> {
                override fun onFailure(call: Call<PaymentData>?, t: Throwable?) {
                    setIsError(t.toString())
                }
                override fun onResponse(call: Call<PaymentData>?, response: Response<PaymentData>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == 1) {

                                setIsSuccessful(response.body()!!)
                            } else {
                                response.body()?.message?.let { setIsError(it) }
                            }
                        } else {
                            setIsError("Error Loading Data")
                        }
                    } else {
                        setIsError("Error Loading Data")
                    }
                }
            })
        }
    }
    private fun executePayments(user_id: String) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(getToken()).payments(user_id)
            call.enqueue(object : Callback<PaymentData> {
                override fun onFailure(call: Call<PaymentData>?, t: Throwable?) {
                    setIsError(t.toString())
                }
                override fun onResponse(call: Call<PaymentData>?, response: Response<PaymentData>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == 1) {

                                setIsSuccessful(response.body()!!)
                            } else {
                                response.body()?.message?.let { setIsError(it) }
                            }
                        } else {
                            setIsError("Error Loading Data")
                        }
                    } else {
                        setIsError("Error Loading Data")
                    }
                }
            })
        }
    }
   private fun executePay(invoice: String,user: String?,amount: String?,phone: String?) {
       GlobalScope.launch(context = Dispatchers.Main) {
           val call = RequestService.getService(getToken()).pay(invoice, user, amount, phone)
           call.enqueue(object : Callback<PaymentData> {
               override fun onFailure(call: Call<PaymentData>?, t: Throwable?) {
                   setIsError(t.toString())
               }
               override fun onResponse(call: Call<PaymentData>?, response: Response<PaymentData>?) {
                   if (response != null) {
                       if (response.isSuccessful) {
                           if (response.body()?.status == 1) {

                               setIsSuccessful(response.body()!!)
                           } else {
                               response.body()?.message?.let { setIsError(it) }
                           }
                       } else {
                           setIsError("Error Loading Data")
                       }
                   } else {
                       setIsError("Error Loading Data")
                   }
               }
           })
       }
   }
    private fun executeCompletePayment(invoice: String,user: String?,amount: String?,phone: String?) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(getToken()).pay(invoice, user, amount, phone)
            call.enqueue(object : Callback<PaymentData> {
                override fun onFailure(call: Call<PaymentData>?, t: Throwable?) {
                    setIsError(t.toString())
                }
                override fun onResponse(call: Call<PaymentData>?, response: Response<PaymentData>?) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body()?.status == 1) {

                                setIsSuccessful(response.body()!!)
                            } else {
                                response.body()?.message?.let { setIsError(it) }
                            }
                        } else {
                            setIsError("Error Loading Data")
                        }
                    } else {
                        setIsError("Error Loading Data")
                    }
                }
            })
        }
    }
    private fun setIsLoading() {
        paymentObservable.postValue(Resource.loading(null))
    }
    private fun setIsSuccessful(parameters: PaymentData) {
        paymentObservable.postValue(Resource.success(parameters))
    }
    private fun setIsError(message: String) {
        paymentObservable.postValue(Resource.error(message, null))
    }
    fun getToken(): String {
        return profileDao.fetch().token.toString()
    }
}