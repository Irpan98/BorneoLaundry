package id.itborneo.borneolaundry.transaction

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionModel(
    val status_payment: String? = null,
    val total_price: String? = null,
    val qty: String? = null,
    val id_packet: String? = null,
    val id: String? = null,
    val customer_name: String? = null,
    val status_progress: String? = null
) : Parcelable