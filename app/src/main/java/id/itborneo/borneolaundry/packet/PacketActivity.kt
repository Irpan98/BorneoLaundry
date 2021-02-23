package id.itborneo.borneolaundry.packet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.itborneo.borneolaundry.R
import id.itborneo.borneolaundry.networks.ApiClient
import kotlinx.android.synthetic.main.activity_packet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PacketActivity : AppCompatActivity() {

    companion object {
        var REQ_ADD = 12
        var REQ_UPDATE = 12
        var EXTRA_DATA = "extra data"

    }

    private lateinit var adapter: PacketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packet)


        initButtonListener()
        initRecyclerview()
        setupAppbar()
        getData()
    }


    private fun setupAppbar() {
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = "Packet"
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

    private fun initButtonListener() {
        btnAdd.setOnClickListener {

            val intent = Intent(this, PacketAddUpdateActivity::class.java)
            startActivityForResult(intent, REQ_ADD)

        }
    }

    private fun initRecyclerview() {
        adapter = PacketAdapter {
            val intent = Intent(this, PacketAddUpdateActivity::class.java)
            intent.putExtra(EXTRA_DATA, it)
            startActivityForResult(intent, REQ_UPDATE)
        }
        rvPacket.adapter = adapter
        rvPacket.layoutManager = LinearLayoutManager(this)
    }


    private fun getData() {

        ApiClient.create().getPacket().enqueue(object : Callback<PacketResponse> {
            override fun onResponse(
                call: Call<PacketResponse>,
                response: Response<PacketResponse>
            ) {
                val list = response?.body()?.data
                if (list != null) {
                    Log.d("PacketActivity", "$list")

                    adapter.list = list as List<PacketModel>
                    adapter.notifyDataSetChanged()

                } else {
                    Log.d("PacketActivity", "data tidak ada")

                }
            }

            override fun onFailure(call: Call<PacketResponse>, t: Throwable) {
                Log.d("PacketActivity", t.message ?: "Unknuwn Error")

            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getData()
    }
}