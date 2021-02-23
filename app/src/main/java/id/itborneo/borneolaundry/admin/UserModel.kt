package id.itborneo.borneolaundry.admin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val password: String? = null,
    val role: String? = null,
    val name: String? = null,
    val id: String? = null,
    val email: String? = null
) : Parcelable