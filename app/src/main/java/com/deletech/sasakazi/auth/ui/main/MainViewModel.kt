package com.deletech.sasakazi.auth.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.deletech.sasakazi.custom.Resource
import com.deletech.sasakazi.models.auth.Auth
import com.deletech.sasakazi.models.auth.Profile
import com.deletech.sasakazi.models.payment.PaymentData
import com.deletech.sasakazi.storage.repositories.PaymentRepository
import com.deletech.sasakazi.storage.repositories.SignInRepository
import com.deletech.sasakazi.storage.repositories.SignupRepository
import kotlin.random.Random

class MainViewModel (application: Application): AndroidViewModel(application) {
    internal  var signInRepository: SignInRepository
    internal var signUpRepository: SignupRepository
    internal var paymentRepository: PaymentRepository
    private val paymentObservable = MediatorLiveData<Resource<PaymentData>>()
    private val signUpObservable = MediatorLiveData<Resource<Auth>>()
    private val signInObservable = MediatorLiveData<Resource<Auth>>()
    init {
        signUpRepository = SignupRepository(application)
        signInRepository = SignInRepository(application)
        paymentRepository = PaymentRepository(application)
        paymentObservable.addSource(paymentRepository.paymentObservable) { data -> paymentObservable.setValue(data) }
        signUpObservable.addSource(signUpRepository.signUPObservable) { data -> signUpObservable.setValue(data) }
        signInObservable.addSource(signInRepository.signInObservable){ data-> signInObservable.setValue(data)}
    }
    fun getRandPassword(n: Int): String
    {
        val characterSet = "003232303232023232023456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val random = Random(System.nanoTime())
        val password = StringBuilder()

        for (i in 0 until n) {
            val rIndex = random.nextInt(characterSet.length)
            password.append(characterSet[rIndex])
        }

        return password.toString()
    }
    fun signUp(parameters: Auth) {
        signUpRepository.signUp(parameters)
    }
    fun signIn(parameters: Auth) {
        signInRepository.signIn(parameters)
    }
    fun saveProfile(data:Auth){
        signInRepository.saveProfile(data)
    }
    fun getUserProfile(): LiveData<Profile> {
        return signInRepository.getProfile()
    }
    fun observeSignUp(): LiveData<Resource<Auth>> {
        return signUpObservable
    }
    fun observePayment(): LiveData<Resource<PaymentData>> {
        return paymentObservable
    }
    fun getPayments(user_id:String){
        paymentRepository.payments(user_id)
    }
    fun makePayment(invoice: String,user: String?,amount: String?,phone: String?){
        paymentRepository.pay(invoice, user, amount, phone)
    }
    fun completePayment(invoice: String,user: String?,amount: String?,phone: String?){
        paymentRepository.completePay(invoice, user, amount, phone)
    }
    fun observeSignIn(): LiveData<Resource<Auth>> {
        return signInObservable
    }
}