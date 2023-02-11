package com.deletech.sasakazi.models.payment
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class PaymentData {
    @SerializedName("error")
    @Expose
    var error = false
    @SerializedName("status")
    @Expose
    var status:Int? = 0
    @SerializedName("success")
    @Expose
    var success = false
    @SerializedName("orders")
    @Expose
    var orders: List<Payments>? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
}