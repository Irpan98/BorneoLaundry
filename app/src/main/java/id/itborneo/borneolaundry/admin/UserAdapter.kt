package id.itborneo.borneolaundry.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.borneolaundry.R
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(val clickListener: (UserModel) -> Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var list = listOf<UserModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: UserModel) {
            itemView.apply {
                tvName.text = data.name
                tvEmail.text = data.email
                tvRole.text = data.role

                setOnClickListener {
                    clickListener(data)
                }
            }
        }

    }


}