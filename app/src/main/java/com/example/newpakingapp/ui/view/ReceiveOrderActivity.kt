package com.example.newpakingapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newpakingapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiveOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_order)
    }
}