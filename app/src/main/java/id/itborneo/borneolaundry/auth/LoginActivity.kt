package id.itborneo.borneolaundry.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import id.itborneo.borneolaundry.HomeActivity
import id.itborneo.borneolaundry.R
import id.itborneo.borneolaundry.admin.UserModel
import id.itborneo.borneolaundry.admin.UserResponse
import id.itborneo.borneolaundry.networks.ApiClient
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    companion object {
        var EXTRA_DATA = "extra data"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonListener()
        setupAppbar()
    }

    private fun buttonListener() {
        btnLogin.setOnClickListener {

            login()

        }
    }

    private fun setupAppbar() {
        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide()
    }

    private fun login() {

        ApiClient.create().login(
            etEmail.text.toString()
        ).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                val list = response?.body()?.data
                if (!list.isNullOrEmpty()) {
                    Log.d("LoginActivity", "$list")

                    if (list[0]?.password == etPassword.text.toString()) {
                        moveToHome(list[0]!!)
                        Toast.makeText(
                            this@LoginActivity,
                            "Berhasil Login",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Email atau Password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    //email salah
                    Toast.makeText(
                        this@LoginActivity,
                        "Email atau Password salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("LoginActivity", "data tidak ada")

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("LoginActivity", t.message ?: "Unknuwn Error")

            }

        })
    }

    private fun moveToHome(user: UserModel) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(EXTRA_DATA, user)
        startActivity(intent)
    }

}