package id.itborneo.borneolaundry.admin

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.itborneo.borneolaundry.R
import id.itborneo.borneolaundry.networks.ApiClient
import kotlinx.android.synthetic.main.activity_admin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : AppCompatActivity() {


    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        setupAppbar()
        initRecyclerview()
        getData()

    }

    private fun setupAppbar() {
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = "Admin"
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerview() {
        adapter = UserAdapter {
        }
        rvUser.adapter = adapter
        rvUser.layoutManager = LinearLayoutManager(this)
    }

    private fun getData() {

        ApiClient.create().getUser().enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                val list = response?.body()?.data
                if (list != null) {
                    Log.d("PacketActivity", "$list")

                    adapter.list = list as List<UserModel>
                    adapter.notifyDataSetChanged()

                } else {
                    Log.d("AdminActivity", "data tidak ada")

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("AdminActivity", t.message ?: "Unknuwn Error")

            }

        })
    }
}