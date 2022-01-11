package com.example.newpakingapp.ui.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newpakingapp.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StartOrderActivity : AppCompatActivity() {

    @Inject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_order)

    }

}