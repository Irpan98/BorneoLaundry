package id.itborneo.borneolaundry.packet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PacketModel(
    val note: String? = null,
    val price: String? = null,
    val name: String? = null,
    val id: String? = null
) : Parcelable
