package id.itborneo.borneolaundry.packet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.borneolaundry.R
import kotlinx.android.synthetic.main.item_packet.view.*

class PacketAdapter(val clickListener: (PacketModel) -> Unit) :
    RecyclerView.Adapter<PacketAdapter.ViewHolder>() {

    var list = listOf<PacketModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_packet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(packet: PacketModel) {
            itemView.apply {
                tvName.text = packet.name
                tvPrice.text = packet.price
                tvNote.text = packet.note

                setOnClickListener {
                    clickListener(packet)
                }
            }
        }

    }


}