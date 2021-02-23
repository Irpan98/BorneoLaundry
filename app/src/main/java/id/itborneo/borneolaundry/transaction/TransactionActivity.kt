package id.itborneo.borneolaundry.transaction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.itborneo.borneolaundry.R
import id.itborneo.borneolaundry.networks.ApiClient
import id.itborneo.borneolaundry.packet.PacketModel
import id.itborneo.borneolaundry.packet.PacketResponse
import kotlinx.android.synthetic.main.activity_transaction.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionActivity : AppCompatActivity() {

    companion object {
        var REQ_ADD = 12
        var REQ_UPDATE = 12
        var EXTRA_DATA = "extra data"
        var EXTRA_PACKET = "extra packet"

    }

    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        buttonListener()
        initRecyclerview()
        setupAppbar()
        getData()
    }

    private fun buttonListener() {
        btnAdd.setOnClickListener {

            ApiClient.create().getPacket().enqueue(object : Callback<PacketResponse> {
                override fun onResponse(
                    call: Call<PacketResponse>,
                    response: Response<PacketResponse>
                ) {
                    val list = response?.body()?.data
                    if (list != null) {
                        Log.d("TransactionActivity", "$list")

                        val intent = Intent(
                            this@TransactionActivity,
                            TransactionAddUpdateActivity::class.java
                        )
                        val listArray = ArrayList<PacketModel>()
                        list.forEach {
                            if (it != null) {
                                listArray.add(it)
                            }
                        }
                        intent.putParcelableArrayListExtra(EXTRA_PACKET, listArray)
                        startActivityForResult(intent, REQ_ADD)

                    } else {
                        Log.d("TransactionActivity", "data tidak ada")

                    }
                }

                override fun onFailure(call: Call<PacketResponse>, t: Throwable) {
                    Log.d("TransactionActivity", t.message ?: "Unknuwn Error")

                }

            })


        }
    }

    private fun setupAppbar() {
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = "Transaksi"
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
        adapter = TransactionAdapter {


            ApiClient.create().getPacket().enqueue(object : Callback<PacketResponse> {
                override fun onResponse(
                    call: Call<PacketResponse>,
                    response: Response<PacketResponse>
                ) {
                    val list = response?.body()?.data
                    if (list != null) {
                        Log.d("TransactionActivity", "$list")


                        val listArray = ArrayList<PacketModel>()
                        list.forEach {
                            if (it != null) {
                                listArray.add(it)
                            }
                        }

                        val intent = Intent(
                            this@TransactionActivity,
                            TransactionAddUpdateActivity::class.java
                        )
                        intent.putParcelableArrayListExtra(EXTRA_PACKET, listArray)
                        intent.putExtra(EXTRA_DATA, it)
                        startActivityForResult(intent, REQ_UPDATE)
                    } else {
                        Log.d("TransactionActivity", "data tidak ada")

                    }
                }

                override fun onFailure(call: Call<PacketResponse>, t: Throwable) {
                    Log.d("TransactionActivity", t.message ?: "Unknuwn Error")

                }

            })


        }
        rvTransaction.adapter = adapter
        rvTransaction.layoutManager = LinearLayoutManager(this)
    }

    private fun getData() {

        ApiClient.create().getTransaction().enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(
                call: Call<TransactionResponse>,
                response: Response<TransactionResponse>
            ) {
                val list = response?.body()?.data
                if (list != null) {
                    Log.d("TransactionActivity", "$list")

                    adapter.list = list as List<TransactionModel>
                    adapter.notifyDataSetChanged()

                } else {
                    Log.d("TransactionActivity", "data tidak ada")

                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                Log.d("TransactionActivity", t.message ?: "Unknuwn Error")

            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getData()
    }
}