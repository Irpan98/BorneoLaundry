package id.itborneo.borneolaundry

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.itborneo.borneolaundry.admin.AdminActivity
import id.itborneo.borneolaundry.admin.UserModel
import id.itborneo.borneolaundry.auth.LoginActivity.Companion.EXTRA_DATA
import id.itborneo.borneolaundry.packet.PacketActivity
import id.itborneo.borneolaundry.transaction.TransactionActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var user: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        retrieveData()
        setupAppbar()
        buttonListener()
        updateUI()

    }

    private fun updateUI() {
        tvName.text = user?.name
    }

    private fun retrieveData() {
        user = intent.getParcelableExtra(EXTRA_DATA)
    }

    private fun setupAppbar() {
        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide()
    }

    private fun buttonListener() {

        btnTransaction.setOnClickListener {

            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)
        }

        btnAdmin.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

        btnPacket.setOnClickListener {
            val intent = Intent(this, PacketActivity::class.java)
            startActivity(intent)
        }

        btnReport.setOnClickListener {
            dialogOnDevelopment()
        }

    }

    private fun dialogOnDevelopment() {
        val dialog = BottomSheetDialog(this)
        val view =
            LayoutInflater.from(this).inflate(R.layout.partial_on_development, rootView, false)
        view.findViewById<Button>(R.id.btnClose).setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }
}