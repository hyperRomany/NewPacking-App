package com.example.newpakingapp.ui.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.newpakingapp.R
import com.example.newpakingapp.data.model.Module
import com.example.newpakingapp.ui.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel:HomeViewModel by viewModels()
    @Inject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

      //  handleButtonsClicks()

    }

    override fun onStart() {
        super.onStart()
        homeViewModel.allWords.observe(this,  {
            setUpView(it)
        })
    }


    private fun setUpView(modules:List<Module>)
    {

        for (module in modules)
        {
            when {
                module.Modules_ID.equals("1") -> {
                    assign_package_in_order.visibility = View.VISIBLE
                }
                module.Modules_ID.equals("2") -> {
                    receive_package_in_order.visibility = View.VISIBLE
                }
                module.Modules_ID.equals("3") -> {
                    manage_package_in_order.visibility = View.VISIBLE
                }
                module.Modules_ID.equals("4") -> {
                    driver_delivery.visibility = View.VISIBLE
                }
                module.Modules_ID.equals("6") -> {
                    reprint_runTimeSheet.visibility = View.VISIBLE
                }
                module.Modules_ID.equals("7") -> {
                    reprint_AWB.visibility = View.VISIBLE
                }
                module.Modules_ID.equals("8") -> {
                    manage_cars.visibility = View.VISIBLE
                }
            }

        }
    }


    private fun handleButtonsClicks()
    {
        assign_package_in_order.setOnClickListener {
            navigateActivity(StartOrderActivity::class.java)
        }

//        receive_package_in_order.setOnClickListener {
//            navigateActivity(ReceiveOrderActivity::class.java)
//        }
//
//        manage_package_in_order.setOnClickListener {
//            navigateActivity(ManageOrderActivity::class.java)
//        }
//
//        driver_delivery.setOnClickListener {
//            navigateActivity(DriverModuleActivity::class.java)
//        }
//
//        reprint_runTimeSheet.setOnClickListener {
//            navigateActivity(ReprintRunTimeSheetActivity::class.java)
//        }
//
//        reprint_AWB.setOnClickListener {
//            navigateActivity(ReprintAWBActivity::class.java)
//        }
//
//        manage_cars.setOnClickListener {
//            navigateActivity(ManageCarsActivity::class.java)
//        }

    }


    private fun navigateActivity(activity : Class<*>?)
    {
        intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }

}