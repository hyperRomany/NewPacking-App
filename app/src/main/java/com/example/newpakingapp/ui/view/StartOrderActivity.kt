package com.example.newpakingapp.ui.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.newpakingapp.R
import com.example.newpakingapp.data.database.AppDatabase
import com.example.newpakingapp.data.model.OrderDataResponse
import com.example.newpakingapp.data.model.OrderHeaderModule
import com.example.newpakingapp.data.model.OrderItemsDetails
import com.example.newpakingapp.ui.viewModel.StartOrderViewModel
import com.example.newpakingapp.utlis.DataStateFlow
import com.example.newpakingapp.utlis.CommonMethod
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_start_order.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StartOrderActivity : AppCompatActivity() {

    private val startOrderViewModel: StartOrderViewModel by viewModels()
    private lateinit var commonMethod: CommonMethod
    private lateinit var appDatabase: AppDatabase
    private lateinit var orderHeaders: List<OrderHeaderModule>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_order)

        initView()

        assign_new_packageToOrder.setOnClickListener {
            if (order_number_start_order.text.isNotEmpty()) {
                val orderNumber: String = order_number_start_order.text.toString()

                getOrderDataFromDB(orderNumber)
            }
            else {
                commonMethod.showToastMessage(R.string.empty_fields)
            }
        }
    }

    private fun initView() {

        commonMethod = CommonMethod(this)
        appDatabase = AppDatabase.getDatabase(this)
        orderHeaders = ArrayList()


        order_number_start_order.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_GO
                || actionId == EditorInfo.IME_ACTION_NEXT
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.action == KeyEvent.ACTION_DOWN
                || event == null
                || event.action == KeyEvent.KEYCODE_ENTER
                || event.action == KeyEvent.KEYCODE_NUMPAD_ENTER
                || event.action == KeyEvent.KEYCODE_DPAD_CENTER
            ) {
                getOrderDataFromDB(order_number_start_order.text.toString())
            }
            false
        }

    }


    private fun loadNewPurchaseOrder(orderNumber: String) {

            if (orderHeaders.isNotEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.delete_order)
                    .setIcon(R.drawable.logo)
                    .setPositiveButton(R.string.confirme) { dialog, _ ->
                        startOrderViewModel.deleteAllHeader(orderNumber)
                        startOrderViewModel.deleteAllOrderDetailsItems(orderNumber)
                        startOrderViewModel.deleteAllTrackingNumbers(orderNumber)
                        startOrderViewModel.deleteAllItemsScanned(orderNumber)
                        getOrderData(orderNumber)
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.decline) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            } else {
                getOrderData(orderNumber)
            }
    }


    private fun getOrderDataFromDB(orderNumber: String) {
        startOrderViewModel.getOrderHeaders(orderNumber)
        lifecycleScope.launchWhenStarted {
            startOrderViewModel.stateFlowData.collect {
                when (it) {
                    is DataStateFlow.Loading -> {
                        handleVisibilityItems(true)
                    }
                    is DataStateFlow.Failure -> {
                        commonMethod.showToastMessage(it.toString())
                        handleVisibilityItems(false)
                    }
                    is DataStateFlow.GetHeaderModuleSuccess -> {
                        orderHeaders = it.orderHeaderModules
                        handleVisibilityItems(false)
                        loadNewPurchaseOrder(orderNumber)
                    }
                    else -> {

                    }

                }

            }
        }
    }

    private fun getOrderData(orderNumber: String) {
        startOrderViewModel.getOrderData(orderNumber)
        lifecycleScope.launchWhenStarted {
            startOrderViewModel.stateFlowFlowResponse.collect {
                when (it) {
                    is DataStateFlow.Loading -> {
                        handleVisibilityItems(true)
                    }
                    is DataStateFlow.Failure -> {
                        handleVisibilityItems(false)
                        commonMethod.showToastMessage(it.msg.toString())
                    }

                    is DataStateFlow.Empty -> {
                        handleVisibilityItems(false)
                        commonMethod.showToastMessage(R.string.empty_fields)
                    }

                    is DataStateFlow.GetOrderDataSuccess -> {
                        handleVisibilityItems(false)
                        validateOrderData(it.orderDataResponse)
                    }

                    else -> {

                    }
                }
            }
        }
    }


    private fun fetchDataToDatabase(orderDataResponse: OrderDataResponse) {
        val orderNumber = orderDataResponse.order_number!!.replace("-", "#")

        val orderHeaderModule = OrderHeaderModule()
        orderHeaderModule.orderNumber = orderDataResponse.order_number!!.replace("-", "*")
        orderHeaderModule.OutBound_delivery = orderDataResponse.OutBound_delivery
        orderHeaderModule.Customer_name = orderDataResponse.customer!!.name
        orderHeaderModule.Customer_phone = orderDataResponse.customer.phone_number
        orderHeaderModule.Customer_code = orderDataResponse.customer.customer_code
        orderHeaderModule.Customer_address_govern = orderDataResponse.customer.address!!.govern
        orderHeaderModule.Customer_address_city = orderDataResponse.customer.address.city
        orderHeaderModule.Customer_address_district = orderDataResponse.customer.address.district
        orderHeaderModule.Customer_address_detail =
            orderDataResponse.customer.address.customer_address_detail
        orderHeaderModule.delivery_date = orderDataResponse.delivery!!.date
        orderHeaderModule.delivery_time = orderDataResponse.delivery.time
        orderHeaderModule.grand_total = orderDataResponse.grand_total
        orderHeaderModule.shipping_fees = orderDataResponse.shipping_fees
        orderHeaderModule.picker_confirmation_time = orderDataResponse.picker_confirmation_time
        orderHeaderModule.currency = orderDataResponse.currency
        orderHeaderModule.Out_From_Loc = orderDataResponse.Out_From_Loc
        orderHeaderModule.reedemed_points_amount = orderDataResponse.reedemed_points_amount
        orderHeaderModule.delivery_method = orderDataResponse.delivery.method

        when (orderDataResponse.payment_method) {
            "cashondelivery" -> {
                orderHeaderModule.payment_method = "كاش عند الاستلام"
            }
            "cardondelivery" -> {
                orderHeaderModule.payment_method = "البطاقه عند الاستلام"
            }
            "robusta_accept_cc" -> {
                orderHeaderModule.payment_method = "تم الدفع - online"
            }
        }
        startOrderViewModel.insertOrderHeaderIntoDB(orderHeaderModule)

        for (orderItem in orderDataResponse.itemsOrderDataDBDetails!!) {
            val orderItemsDetails = OrderItemsDetails()
            orderItemsDetails.name = orderItem.name
            orderItemsDetails.price = orderItem.price
            orderItemsDetails.quantity = orderItem.quantity
            orderItemsDetails.barcode = orderItem.barcode
            orderItemsDetails.unite = orderItem.unite
            orderItemsDetails.orderNumber = orderDataResponse.order_number!!.replace("-", "*")

            startOrderViewModel.insertOrderDetails(orderItemsDetails)
        }

        navigateActivity(AssignItemIntoPackage::class.java, true, orderNumber)

    }

    private fun handleVisibilityItems(loading: Boolean) {
        if (loading) {
            startOrder_progress.visibility = View.VISIBLE
            assign_new_packageToOrder.visibility = View.INVISIBLE
            order_number_start_order.isEnabled = false
            open_last_operation.visibility = View.INVISIBLE
            print_AWB.visibility = View.INVISIBLE
            edit_package.visibility = View.INVISIBLE
        } else {
            startOrder_progress.visibility = View.INVISIBLE
            assign_new_packageToOrder.visibility = View.VISIBLE
            order_number_start_order.isEnabled = true
            open_last_operation.visibility = View.VISIBLE
            print_AWB.visibility = View.VISIBLE
            edit_package.visibility = View.VISIBLE
        }
    }

    private fun validateOrderData(orderDataResponse: OrderDataResponse) {
        if (orderDataResponse.status == "closed") {
            if (orderDataResponse.OutBound_delivery != null) {
                fetchDataToDatabase(orderDataResponse)
            } else {
                commonMethod.showToastMessage(R.string.outboundDeliveryNull)
            }
        } else {
            commonMethod.showToastMessage("حالة هذا الطلب هى " + orderDataResponse.status)
        }
    }

    private fun navigateActivity(activity: Class<*>, isAssignItem: Boolean, orderNumber: String) {
        val intent = Intent(this, activity)
        if (isAssignItem) {
            intent.putExtra("AddNewPackageORAddForExistPackage", "New")
            intent.putExtra("order_number", orderNumber)
        }
        startActivity(intent)
        finish()
    }

    private fun createAlertDialog(layout:Int)
    {
        val view = LayoutInflater.from(this).inflate(layout,null)
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setView(view)
            .setIcon(R.drawable.logo)
            .create()
            .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateActivity(HomeActivity::class.java, false, "")
    }

}