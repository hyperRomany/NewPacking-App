package com.example.newpakingapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.newpakingapp.R
import com.example.newpakingapp.data.model.OrderDetailsItemsScanned
import com.example.newpakingapp.data.model.OrderItemsDetails
import com.example.newpakingapp.ui.viewModel.AssignItemToPackageViewModel
import com.example.newpakingapp.utlis.CommonMethod
import com.example.newpakingapp.utlis.DataStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_assign_item_into_package.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AssignItemIntoPackageActivity : AppCompatActivity() {
    private var packageType: String = ""
    private var orderNumber: String = ""
    private lateinit var orderItems : ArrayList<OrderItemsDetails>
    private val assignItemViewModel:AssignItemToPackageViewModel by viewModels()
    private lateinit var commonMethod: CommonMethod


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_item_into_package)

        orderItems = ArrayList()
        commonMethod = CommonMethod(this)

        if (intent != null)
        {
            orderNumber = intent.extras!!.getString("order_number").toString()
            packageType = intent.extras!!.getString("PackageType").toString()

            getAllItemsOrderInDetails()
        }
        else{
            commonMethod.showToastMessage("خطأ فى رقم الاوردر")
        }

        add_item_to_package_btn.setOnClickListener {
            val barcode = assign_item_to_package_barcode.text.toString()
            searchBarcode(barcode)
        }
    }

    private fun getAllItemsOrderInDetails(){
        assignItemViewModel.getAllItemsInDetails(orderNumber)
        lifecycleScope.launchWhenStarted {
            assignItemViewModel.stateFlowData.collect {
                when(it){
                    is DataStateFlow.GetAllItemsInDetails -> {
                        orderItems.addAll(it.allItems)
                    }
                    else ->{

                    }
                }
            }
        }
    }

    private fun searchBarcode(barcode: String){
        if(barcode.substring(0,2) == "23"){
            validateWeightedBarcode(barcode)
        }
        else{
            validateNormalBarcode(barcode)
        }
    }


    private fun validateNormalBarcode(barcode: String) {

    }


    private fun validateWeightedBarcode(barcode: String) {
        if (barcode.length == 13) {
            val barcodeQuantity: Float = (barcode.substring(7, 9) + "." + barcode.substring(9, 12)).toFloat()
            val barcodeSearch = barcode.substring(0,7)
            val itemIndex = searchItemInListRecursive(0, barcodeSearch)
            if (itemIndex != -1)
            {
//                if (barcodeQuantity < 10)
//                {
//                    assign_item_to_package_barcode.error = "تم إدخال قيمه أقل من 10 جرام"
//                    assign_item_to_package_barcode.setText("")
//                    assign_item_to_package_barcode.requestFocus()
//                }
                insertScannedItem(orderItems[itemIndex])
            }

            }
        }

    private fun insertScannedItem(orderItemsDetails: OrderItemsDetails) {
        val orderDetailsItemsScanned = OrderDetailsItemsScanned()
        orderDetailsItemsScanned.Order_number = orderItemsDetails.orderNumber
        orderDetailsItemsScanned.name = orderItemsDetails.name
        orderDetailsItemsScanned.price = orderItemsDetails.price
        orderDetailsItemsScanned.quantity = orderItemsDetails.quantity
        orderDetailsItemsScanned.barcode = orderItemsDetails.barcode
        orderDetailsItemsScanned.unitOfMeasure = orderItemsDetails.unitOfMeasure
        orderDetailsItemsScanned.trackingNumber = 0.toString()

        assignItemViewModel.insertScannedItem(orderDetailsItemsScanned)
        lifecycleScope.launchWhenStarted {
            assignItemViewModel.stateFlowInsert.collect{
                when (it){
                    is DataStateFlow.Success -> {
                        commonMethod.showToastMessage("تم")
                    }
                    else ->{

                    }
                }
            }
        }
    }


    override fun onBackPressed() {
        navigateActivity(StartOrderActivity::class.java)
    }


    private fun navigateActivity(activity: Class<*>){
        val intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }

    private fun searchItemInListRecursive(index:Int, barcode: String) : Int{

        return when {
            index > orderItems.size -> {
                -1
            }
            orderItems[index].barcode!!.substring(0,7) == barcode -> {
                index
            }
            else -> searchItemInListRecursive(index + 1, barcode)
        }

    }

}