package id.itborneo.borneolaundry.packet

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import id.itborneo.borneolaundry.R
import id.itborneo.borneolaundry.networks.ApiClient
import id.itborneo.borneolaundry.networks.DefaultResponse
import id.itborneo.borneolaundry.packet.PacketActivity.Companion.EXTRA_DATA
import kotlinx.android.synthetic.main.activity_packet_add_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PacketAddUpdateActivity : AppCompatActivity() {

    private var data: PacketModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packet_add_update)

        retrieveData()
        setupAppbar()
        updateUI()
        buttonListener()
    }

    private fun setupAppbar() {
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = if (data == null) "Add Packet" else "Update Packet"
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

    private fun updateUI() {
        if (data != null) {
            etName.setText(data?.name)
            etPrice.setText(data?.price)
            etNote.setText(data?.note)
            btnAddUpdate.text = "Update Paket"
        }else{
            btnAddUpdate.text = "Tambah Paket"
            btnDelete.visibility = View.GONE
        }
    }

    private fun retrieveData() {
        data = intent.getParcelableExtra(EXTRA_DATA)
    }

    private fun buttonListener() {
        btnAddUpdate.setOnClickListener {
            Log.d("buttonListener", "btnAddUpdate")
            if (data == null) {
                addData()
            } else {
                updateData()
            }
        }

        btnDelete.setOnClickListener {
            deleteData()
        }
    }

    private fun deleteData() {
        ApiClient.create()
            .deletePacket(data?.id.toString())
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    finish()
                    setResult(RESULT_OK, intent)

                    Toast.makeText(
                        this@PacketAddUpdateActivity,
                        "Paket berhasil Dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("PacketAddUpdateActivity", "error onFailure delete ${t.message}")
                }

            })
    }

    private fun updateData() {
        ApiClient.create()
            .updatePacket(
                data?.id.toString(),
                etName.text.toString(),
                etPrice.text.toString(),
                etNote.text.toString()
            )
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    finish()
                    setResult(RESULT_OK, intent)

                    Toast.makeText(
                        this@PacketAddUpdateActivity,
                        "Paket berhasil diupdate",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("PacketAddUpdateActivity", "error onFailure updateData ${t.message}")
                }

            })
    }

    private fun addData() {
        ApiClient.create()
            .addPacket(etName.text.toString(), etPrice.text.toString(), etNote.text.toString())
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    finish()
                    setResult(RESULT_OK, intent)

                    Toast.makeText(
                        this@PacketAddUpdateActivity,
                        "Paket berhasil ditambahkan",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("PacketAddUpdateActivity", "error onFailure addData ${t.message}")
                }

            })
    }
}