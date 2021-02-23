package id.itborneo.borneolaundry.transaction

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import id.itborneo.borneolaundry.R
import id.itborneo.borneolaundry.networks.ApiClient
import id.itborneo.borneolaundry.networks.DefaultResponse
import id.itborneo.borneolaundry.packet.PacketModel
import id.itborneo.borneolaundry.transaction.TransactionActivity.Companion.EXTRA_DATA
import id.itborneo.borneolaundry.transaction.TransactionActivity.Companion.EXTRA_PACKET
import kotlinx.android.synthetic.main.activity_packet_add_update.*
import kotlinx.android.synthetic.main.activity_packet_add_update.btnAddUpdate
import kotlinx.android.synthetic.main.activity_packet_add_update.btnDelete
import kotlinx.android.synthetic.main.activity_transaction_add_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionAddUpdateActivity : AppCompatActivity() {

    private var data: TransactionModel? = null

    private var paymentStatus: String? = null
    private var progressStatus: String? = null
    private var packetId: String? = null

    private var listPacket: ArrayList<PacketModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_add_update)

        retriveData()
        updateUI()
        setupAppbar()
        buttonListener()
        initPopUpPacketList()

    }

    private fun updateUI() {
        if (data != null) {

            etCustomerName.setText(data?.customer_name)
            etQty.setText(data?.qty)
            etTotalPrice.setText(data?.total_price)

            btnAddUpdate.text = "Update Transaksi"

            progressStatus = data?.status_progress
//            'Baru','Proses','Selesai','Diambil'
            when (progressStatus) {
                "Baru" -> btnGroupProgress.check(R.id.btnProgressNew)
                "Proses" -> btnGroupProgress.check(R.id.btnProgressProcess)
                "Selesai" -> btnGroupProgress.check(R.id.btnProgressDone)
                "Diambil" -> btnGroupProgress.check(R.id.btnProgressTaken)
            }


            paymentStatus = data?.status_payment
            when (paymentStatus) {
                "Dibayar" -> btnGroupPayment.check(R.id.btnPaymentPaid)
                else -> btnGroupPayment.check(R.id.btnPaymentNotYet)
            }



            packetId = data?.id_packet

            listPacket?.forEach {
                if (packetId == it.id) {
                    btnPacket.setText(it.name)
                }
            }


        } else {

            btnAddUpdate.text = "Tambah Transaksi"
            btnDelete.visibility = View.GONE
        }
    }

    private fun retriveData() {
        data = intent.getParcelableExtra(EXTRA_DATA)
        listPacket = intent.getParcelableArrayListExtra(EXTRA_PACKET)
    }


    private fun setupAppbar() {
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = if (data == null) "Add Transaksi" else "Update Transaksi"
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


    private fun buttonListener() {
        btnAddUpdate.setOnClickListener {
            if (data == null) {
                addData()
            } else {
                updateData()
            }
        }

        btnDelete.setOnClickListener {
            deleteData()
        }

        btnGroupPayment.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                //'Dibayar','Belum_Dibayar'
                when (checkedId) {
                    R.id.btnPaymentPaid -> paymentStatus = "Dibayar"
                    R.id.btnPaymentNotYet -> paymentStatus = "Belum_Dibayar"
                }
            }

        }

//        'Baru','Proses','Selesai','Diambil'
        btnGroupProgress.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnProgressNew -> progressStatus = "Baru"
                    R.id.btnProgressProcess -> progressStatus = "Proses"
                    R.id.btnProgressDone -> progressStatus = "Selesai"
                    R.id.btnProgressTaken -> progressStatus = "Diambil"

                }
            }
        }


    }

    private fun initPopUpPacketList() {
        val items = mutableListOf<String>()

        listPacket?.forEach {
            it.name?.let { it1 -> items.add(it1) }
        }

        val listPopWindow = ListPopupWindow(this, null, R.attr.listPopupWindowStyle)
        listPopWindow.anchorView = btnPacket
        val adapter = ArrayAdapter(this, R.layout.list_popup_window_item, items)
        listPopWindow.setAdapter(adapter)

        listPopWindow.setOnItemClickListener { parent, view, position, id ->
            btnPacket.text = listPacket?.get(position)?.name
            packetId = listPacket?.get(position)?.id
            listPopWindow.dismiss()
        }

        btnPacket.setOnClickListener {
            Log.d("Transaksi Pop up", listPacket?.size.toString())
            listPopWindow.show()
        }


    }

    private fun deleteData() {
        ApiClient.create()
            .deleteTransaction(data?.id.toString())
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    finish()
                    setResult(RESULT_OK, intent)

                    Toast.makeText(
                        this@TransactionAddUpdateActivity,
                        "Transaksi berhasil Dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("TransactionAddUpdate", "error onFailure delete ${t.message}")
                }

            })
    }

    private fun updateData() {
        ApiClient.create()
            .updateTransaction(
                data?.id.toString(),
                etCustomerName.text.toString(),
                etQty.text.toString(),
                progressStatus!!,
                paymentStatus!!,
                etTotalPrice.text.toString(),
                packetId!!
            )
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    finish()
                    setResult(RESULT_OK, intent)

                    Toast.makeText(
                        this@TransactionAddUpdateActivity,
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
            .addTransaction(
                etCustomerName.text.toString(),
                etQty.text.toString(),
                progressStatus!!,
                paymentStatus!!,
                etTotalPrice.text.toString(),
                packetId!!
            )
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    finish()
                    setResult(RESULT_OK, intent)

                    Toast.makeText(
                        this@TransactionAddUpdateActivity,
                        "Transaksi berhasil ditambahkan",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("TransactionAddUpdate", "error onFailure addData ${t.message}")
                }

            })
    }


}
