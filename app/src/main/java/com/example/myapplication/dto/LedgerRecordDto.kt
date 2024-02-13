package com.example.myapplication.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LedgerRecordDto(
    @SerialName("id")
    val id: Int,
    @SerialName("created_at")
    val created: Long,
    @SerialName("type")
    val type: String,
    @SerialName("amount")
    val amount: Double
)

val LedgerRecordDto.asTransaction: LedgerTransaction
    get() = when (this.type.uppercase()) {
        "INCOME", "INCOME122" ->
            LedgerTransaction.Income(id = id, amount = amount, created = created)

        "EXPENSE" ->
            LedgerTransaction.Expense(id = id, amount = amount, created = created)

        else ->
            throw Exception("Invalid transaction type for ID ${this.id}")
    }

sealed class LedgerTransaction(
    open val amount: Double, open val created: Long, open val id: Int
) {
    data class Income(
        override val amount: Double, override val created: Long, override val id: Int
    ): LedgerTransaction(amount = amount, created = created, id = id)
    data class Expense(
        override val amount: Double, override val created: Long, override val id: Int
    ): LedgerTransaction(amount = amount, created = created, id = id)
}
