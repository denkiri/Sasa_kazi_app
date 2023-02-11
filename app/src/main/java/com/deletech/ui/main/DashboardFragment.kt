package com.deletech.ui.main
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.deletech.adapter.ReceiptAdapter
import com.deletech.sasakazi.auth.ui.main.MainViewModel
import com.deletech.sasakazi.R
import com.deletech.sasakazi.custom.Resource
import com.deletech.sasakazi.custom.Status
import com.deletech.sasakazi.models.payment.PaymentData
import com.deletech.sasakazi.models.payment.Payments
import com.deletech.sasakazi.network.NetworkUtils
import com.deletech.sasakazi.utils.OnItemClick
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
class DashboardFragment : Fragment() {
    var invoice:String?=null
  var  id:String?=null
    var phone:String?=null
    lateinit var receiptAdapter: ReceiptAdapter
    private lateinit var receipt: ArrayList<Payments>

    companion object {
        fun newInstance() = DashboardFragment()
    }
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setUpUI()
        initView()
        observers()
        notification.isSelected = true
      buy_white.setOnClickListener {
          viewModel.makePayment(viewModel.getRandPassword(8),id.toString(),"200".trim(),phone.toString())
      }
        buy_black.setOnClickListener {
            viewModel.makePayment(viewModel.getRandPassword(8),id.toString(),"250".trim(),phone.toString())
        }
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        itemsswipetorefresh.setColorSchemeColors(Color.GRAY)
        itemsswipetorefresh.setOnRefreshListener {
            init()
            itemsswipetorefresh.isRefreshing = false
        }
    }
    fun observers() {
        viewModel.observePayment().observe(
            viewLifecycleOwner
        ) {
            setStatus(it)
            if (it.status == Status.SUCCESS) {
                if (it.data?.orders != null && it.data.orders!!.isNotEmpty()) {

                    setUpPayments(it.data.orders as ArrayList<Payments>)

                } else {
                    setUpPayments(ArrayList())
                }
            }
        }
    }
    private fun setStatus(data: Resource<PaymentData>) {
        empty_layout.visibility = View.GONE
        main.visibility = View.VISIBLE
        val status: Status = data.status
        if (status == Status.LOADING) {
            avi.visibility = View.VISIBLE
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else if (status == Status.ERROR || status == Status.SUCCESS) {
            avi.visibility = View.GONE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        if (status == Status.ERROR) {
            if (data.message != null) {
                empty_text.text = data.message
                view?.let { Snackbar.make(it, data.message.toString(), Snackbar.LENGTH_LONG).show() }
            }

            empty_layout.visibility = View.VISIBLE
            main.visibility = View.GONE
            empty_button.setOnClickListener({ init() })
        }
    }

    private fun setUpUI() {
        viewModel.getUserProfile().observe(viewLifecycleOwner, Observer {
            try {
                intro.text ="Welcome "+it.name
                id=it.id.toString()
                phone=it.mobile
                viewModel.getPayments(it.id.toString())
            } catch (_: Exception) {
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    fun init() {
        if (NetworkUtils.isConnected(requireContext())) {
            viewModel.getPayments(id.toString())
        }
    }
    private fun setUpPayments(receipt: ArrayList<Payments>) {
        this.receipt = receipt
        receiptAdapter.refresh(this.receipt)
        Handler().postDelayed(Runnable {
        }, 1)
    }
    private fun initView(){
        receipt = ArrayList()
        receiptAdapter = context?.let {
            ReceiptAdapter(0, it, receipt, object : OnItemClick {
                override fun selected(pos: Int) {
                }
                override fun onClickListener(position1: Int) {
                    viewModel.completePayment(receipt[position1].invoiceNumber.toString(),receipt[position1].name.toString(),receipt[position1].amount,receipt[position1].phoneNumber.toString())
                }
            })
        }!!
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = receiptAdapter
        receiptAdapter.notifyDataSetChanged()
    }

}