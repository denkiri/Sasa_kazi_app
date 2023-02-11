package com.deletech.adapter
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.deletech.sasakazi.R
import com.deletech.sasakazi.utils.OnItemClick
import java.lang.ref.WeakReference
class ReceiptViewHolder (type: Int, itemView: View, listener: OnItemClick): RecyclerView.ViewHolder(itemView), View.OnClickListener{
    override fun onClick(v: View?) {
        if (v ==buy ) {
            listenerWeakReference.get()?.onClickListener(adapterPosition)
        }
    }
    private val listenerWeakReference: WeakReference<OnItemClick> = WeakReference(listener)
    var itemVew: View
    var card_view: CardView
    var time: TextView
    var date:TextView
    var invoice:TextView
    var price:TextView
    var code:TextView
    var phone:TextView
    var paid:TextView
    var buy:Button
    var paymentDetails:TextView
    init {
        this.itemVew=itemView
        time=itemView.findViewById(R.id.time)
        paid=itemView.findViewById(R.id.paid)
        buy=itemView.findViewById(R.id.buy)
        date=itemView.findViewById(R.id.date)
        card_view=itemView.findViewById(R.id.card_view)
        invoice=itemView.findViewById(R.id.invoice)
        price=itemView.findViewById(R.id.price)
        code=itemView.findViewById(R.id.code)
        phone=itemView.findViewById(R.id.phone)
        paymentDetails=itemView.findViewById(R.id.paymentDetails)
        if (type==0) {
            buy.setOnClickListener(this)

        }
    }
}