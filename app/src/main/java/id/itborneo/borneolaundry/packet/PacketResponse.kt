package id.itborneo.borneolaundry.packet

data class PacketResponse(
	val data: List<PacketModel?>? = null,
	val message: String? = null,
	val isSuccess: Boolean? = null
)


