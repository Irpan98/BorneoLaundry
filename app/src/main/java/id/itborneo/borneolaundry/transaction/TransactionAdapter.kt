package id.itborneo.borneolaundry.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.borneolaundry.R
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionAdapter(val clickListener: (TransactionModel) -> Unit) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    var list = listOf<TransactionModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(transaction: TransactionModel) {
            itemView.apply {
                tvCustomerName.text = transaction.customer_name
                tvQty.text = transaction.qty
                tvStatuProgress.text = transaction.status_progress

                setOnClickListener {
                    clickListener(transaction)
                }
            }
        }

    }


}