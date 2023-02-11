package com.deletech.sasakazi.models.payment
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class Payments {
    @SerializedName("id")
    @Expose
    var id:Int? = 0
    @SerializedName("invoice_number")
    @Expose
    var invoiceNumber: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("amount")
    @Expose
    var amount: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("month")
    @Expose
    var month: String? = null
    @SerializedName("year")
    @Expose
    var year: String? = null
    @SerializedName("paymentStatus")
    @Expose
    var paymentStatus: String? = null
    @SerializedName("payment_details")
    @Expose
    var paymentDetails: String? = null
    @SerializedName("mpesa_amount")
    @Expose
    var mpesaAmount: String? = null
    @SerializedName("MpesaReceiptNumber")
    @Expose
    var mpesaReceiptNumber: String? = null
    @SerializedName("TransactionDate")
    @Expose
    var transactionDate: String? = null
    @SerializedName("PhoneNumber")
    @Expose
    var phoneNumber: String? = null
}