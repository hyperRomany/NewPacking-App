package com.example.newpakingapp.utlis

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.newpakingapp.ui.view.StartOrderActivity

class CommonMethod  constructor(private val context: Context){

    @SuppressLint("MissingPermission")
    fun checkNetworkConnection(): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return !(activeNetworkInfo == null || !activeNetworkInfo.isConnected)
    }

    fun showToastMessage(msg:Int){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun showToastMessage(msg:String){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }


}