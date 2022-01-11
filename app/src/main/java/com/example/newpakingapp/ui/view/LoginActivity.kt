package com.example.newpakingapp.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.newpakingapp.R
import com.example.newpakingapp.ui.viewModel.LoginViewModel
import com.example.newpakingapp.utlis.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.net.URLConnection
import javax.inject.Inject
import kotlin.Exception


@AndroidEntryPoint
@SuppressLint("SetTextI18n")

class LoginActivity : AppCompatActivity() {


    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var versionName: String
    private lateinit var versionCode: String
    private lateinit var commonMethod: CommonMethod
    private var updateDownloadORNot = false
    val progressDownload = 0
    private var mProgressDialog: ProgressDialog? = null

    @Inject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        commonMethod = CommonMethod(this)


        getVersionFromServer()


        login.setOnClickListener {
            if (login_password.text.isEmpty() || login_username.text.isEmpty()) {
                commonMethod.showToastMessage(R.string.please_enter_all_fields)
            } else if (!commonMethod.checkNetworkConnection()) {
                commonMethod.showToastMessage(R.string.please_check_network)
            }
//            } else if (getAppVersion().toDouble() < versionName.toDouble()) {
//                commonMethod.showToastMessage(R.string.new_version_found)
//
//            }
            else {
                val userName: String = login_username.text.toString()
                val password: String = login_password.text.toString()
                loginFun(userName, password)
            }

        }


    }

    private fun loginFun(userName: String, password: String) {
        val user: HashMap<String, String> = HashMap()

        user["username"] = userName
        user["password"] = password

        loginViewModel.getLoginResponse(user)
        lifecycleScope.launchWhenStarted {
            loginViewModel.stateFlowApk.collect {
                when (it) {
                    is ApiState.Loading -> {
                        login_progress.visibility = View.VISIBLE
                        login.visibility = View.GONE
                    }
                    is ApiState.SuccessLogin -> {
                        loginViewModel.deleteAllModules()
                        loginViewModel.deleteAllUsers()

                        loginViewModel.insertModule(it.loginResponse.modules!!)
                        loginViewModel.insertUser(it.loginResponse.userInfo!![0])

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        login_progress.visibility = View.GONE
                        login.visibility = View.VISIBLE
                    }
                    is ApiState.Failure -> {
                        login_progress.visibility = View.GONE
                        login.visibility = View.VISIBLE
                        Toast.makeText(this@LoginActivity, it.msg.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    is ApiState.Empty -> commonMethod.showToastMessage(R.string.empty_fields)

                    else -> {

                    }

                }
            }
        }
    }


    private fun getVersionFromServer() {
        loginViewModel.getVersion()

        lifecycleScope.launchWhenStarted {
            loginViewModel.stateFlowApk.collect {
                when (it) {
                    is ApiState.Success -> {
                        versionName = it.data.Version_Name.toString()
                        versionCode = it.data.Version_Code.toString()

                        login_txt_virsion.text = "V $versionName $MAGENTO_TYPE"

                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this@LoginActivity,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        ) {

                            ActivityCompat.requestPermissions(
                                this@LoginActivity,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                1
                            )
                        }
                    }
                    else -> {

                    }

                }
            }
        }
    }

    private fun getAppVersion(): String {
        var version = "0.0"

        try {
            val pm = packageManager
            val pInfo = pm.getPackageInfo(packageName, 0)
            version = pInfo.versionName

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return version
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                commonMethod.showToastMessage(R.string.permission_access)
                DownloadFileFromURL(updateDownloadORNot,progressDownload , mProgressDialog).execute(APKS_URL_WITHOUT_NAMES+"PackingApp_V"+versionName+".apk","PackingApp_V"+versionName+".apk")
            }
        }
    }



    class DownloadFileFromURL constructor(downloadOrNot : Boolean, progress: Int, mProgressDialog: ProgressDialog?) :
        AsyncTask<kotlin.String?, kotlin.String?, kotlin.String?>() {

        var downloaded = downloadOrNot
        val progressVal = progress
        val mProgress = mProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()


        }

        override fun doInBackground(vararg p0: String?): String? {
            var count:Int = 0
            try {
                val url:URL = URL(p0[0])
                val urlConnection:URLConnection = url.openConnection()
                urlConnection.connect()

                val legnth:Int = urlConnection.contentLength
                val input:InputStream = BufferedInputStream(url.openStream(), 8192)
                val output:OutputStream = FileOutputStream(Environment.getExternalStorageDirectory()
                    .toString()
                        + "/" + p0[0])
                val data = ByteArray(1024)
                var total:Long = 0
                while (input.read(data).also { count = it } != -1)
                {
                    total += count
                    publishProgress("" + (total * 100 / legnth) as Int)
                    output.write(data, 0, count)
                }

                downloaded = true


                output.flush()
                output.close()
                input.close()

            }catch (e:Exception)
            {
                downloaded = false
            }
            return null
        }

        override fun onProgressUpdate(vararg values: String?) {
            (values[0] as Int).also { mProgress!!.progress = it }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }
    }

}