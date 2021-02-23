package id.itborneo.borneolaundry.networks

import id.itborneo.borneolaundry.admin.UserResponse
import id.itborneo.borneolaundry.packet.PacketResponse
import id.itborneo.borneolaundry.transaction.TransactionResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET("packet/getPacket.php")
    fun getPacket(): Call<PacketResponse>

    @FormUrlEncoded
    @POST("packet/insertPacket.php")
    fun addPacket(
        @Field("name") name: String,
        @Field("price") price: String,
        @Field("note") note: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("packet/updatePacket.php")
    fun updatePacket(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("price") price: String,
        @Field("note") note: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("packet/deletePacket.php")
    fun deletePacket(
        @Field("id") id: String,
    ): Call<DefaultResponse>

    ////////////////////////////////////////////////////////////

    @GET("transaction/getTransaction.php")
    fun getTransaction(): Call<TransactionResponse>

    @FormUrlEncoded
    @POST("transaction/insertTransaction.php")
    fun addTransaction(
        @Field("customer_name") customerName: String,
        @Field("qty") qty: String,
        @Field("status_progress") statusProgress: String,
        @Field("status_payment") statusPayment: String,
        @Field("total_price") totalPrice: String,
        @Field("id_packet") idPacket: String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("transaction/updateTransaction.php")
    fun updateTransaction(
        @Field("id") id: String,
        @Field("customer_name") customerName: String,
        @Field("qty") qty: String,
        @Field("status_progress") statusProgress: String,
        @Field("status_payment") statusPayment: String,
        @Field("total_price") totalPrice: String,
        @Field("id_packet") idPacket: String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("transaction/deleteTransaction.php")
    fun deleteTransaction(
        @Field("id") id: String,
    ): Call<DefaultResponse>


    ///////////////////////////////////////////////////////////////////


    @GET("auth/login.php")
    fun getUser(): Call<UserResponse>

    @GET("auth/login.php")
    fun login(
        @Query("email") email: String
    ): Call<UserResponse>
}