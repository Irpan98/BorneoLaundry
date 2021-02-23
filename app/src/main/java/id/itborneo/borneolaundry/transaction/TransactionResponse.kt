package id.itborneo.borneolaundry.transaction

data class TransactionResponse(
    val data: List<TransactionModel?>? = null,
    val message: String? = null,
    val isSuccess: Boolean? = null
)


