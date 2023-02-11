package com.deletech.sasakazi.network
import com.deletech.sasakazi.models.auth.Auth
import com.deletech.sasakazi.models.payment.PaymentData
import retrofit2.Call
import retrofit2.http.*
interface EndPoints {
    @FormUrlEncoded
    @POST("aouth/signin.php")
    fun signIn(@Field("email") email: String?, @Field("password") password: String?): Call<Auth>
    @FormUrlEncoded
    @POST("aouth/signup.php")
    fun signUp(@Field("email") email: String?, @Field("mobile") mobile: String?,@Field("name") name: String?,@Field("password") password: String?): Call<Auth>
    @FormUrlEncoded
    @POST("payment/completePayments.php")
    fun completePayment(@Field("user_id") userId: String?): Call<PaymentData>
    @FormUrlEncoded
    @POST("payment/pendingPayments.php")
    fun pendingPayment(@Field("user_id") userId: String?): Call<PaymentData>
    @FormUrlEncoded
    @POST("payment/payments.php")
    fun payments(@Field("user_id") userId: String?): Call<PaymentData>
    @FormUrlEncoded
    @POST("payment/pay.php")
    fun pay(@Field("invoice") invoice: String?,@Field("user") user: String?,@Field("amount") amount: String?,@Field("phone") phone: String?): Call<PaymentData>
    @FormUrlEncoded
    @POST("payment/completePayment.php")
    fun completePayment(@Field("invoice") invoice: String?,@Field("user") user: String?,@Field("amount") amount: String?,@Field("phone") phone: String?): Call<PaymentData>


}