package com.deletech.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deletech.sasakazi.R
import com.deletech.sasakazi.models.payment.Payments
import com.deletech.sasakazi.utils.OnItemClick
class ReceiptAdapter (private val type: Int, private  val context: Context, private  var modelList: List<Payments>?, private val recyclerListener: OnItemClick):
    RecyclerView.Adapter<ReceiptViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        var itemView: View? =null
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.receipt,parent,false)
        return ReceiptViewHolder(type,itemView!!,recyclerListener)
    }
    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        val model= modelList!![position]
        if (model.mpesaReceiptNumber==null && model.paymentStatus=="0"){
            holder.buy.visibility=View.INVISIBLE
            holder.paid.visibility=View.INVISIBLE
        }
        if (model.mpesaReceiptNumber==null && model.paymentStatus=="2"){
            holder.buy.visibility=View.VISIBLE
            holder.paid.visibility=View.INVISIBLE
        }
        if (model.mpesaReceiptNumber!==null && model.paymentStatus=="1"){
            holder.buy.visibility=View.INVISIBLE
            holder.paid.visibility=View.VISIBLE
        }
        holder.phone.text=model.phoneNumber.toString()
        holder.code.text=model.mpesaReceiptNumber.toString()
        holder.price.text=model.mpesaAmount.toString()
        holder.invoice.text=model.invoiceNumber.toString()
        holder.paymentDetails.text=model.paymentDetails.toString()
        holder.date.text=model.date.toString()
    }
    override fun getItemCount(): Int {
        return  if (null!= modelList)modelList!!.size else 0
    }
    fun refresh(modelList: List<Payments>?){
        this.modelList =modelList
        notifyDataSetChanged()
    }
}