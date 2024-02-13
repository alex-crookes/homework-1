package com.example.myapplication

import com.example.myapplication.dto.LedgerRecordDto
import com.example.myapplication.dto.LedgerTransaction
import com.example.myapplication.dto.asTransaction
import com.example.myapplication.dummydata.dummyBalanceChart
import com.example.myapplication.dummydata.testLedgerApiResult
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/*
@TODO
- Add function load Data from API on startup (essentially already unit tested)
- Add additional functions for transforming data
- Convert to View Models / live data
 */

typealias Epoch = Long

interface Ledger {
    val balanceChart: ChartModel
}

class LedgerDomain: Ledger {

    internal var ledgerEntries = mutableMapOf<Epoch, LedgerTransaction>()


    init {
        val initialLedger = Json.decodeFromString<List<LedgerRecordDto>>(testLedgerApiResult).map {
            it.asTransaction
        }

        ledgerEntries = initialLedger.associateBy { it.created }.toMutableMap()

    }

    val incomeEntries: List<LedgerTransaction.Income>
        get() = ledgerEntries.values.filterIsInstance(LedgerTransaction.Income::class.java)

    val expenseEntries: List<LedgerTransaction.Expense>
        get() = ledgerEntries.values.filterIsInstance(LedgerTransaction.Expense::class.java)

    override val balanceChart: ChartModel
        get() = dummyBalanceChart

//    val incomeEntriesByDay: Map<Date, LedgerTransaction>
//        get() {
//
//            ledgerEntries.map {
//                it.key.toDMY to it
//            }
//
//
//            val instant = Instant.ofEpochMilli(12345L)
//            val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
//
//
//
//
//            // need to convert EPOCH to Calendar / etc object
//            // group by Day and sum results
//            // would be easier in a DB layer...
//
//        }

    //private val Long.Day

    private val Epoch.toDMY: String
        get() {
            val instant = Instant.ofEpochMilli(this)
            val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

            return "${date.dayOfMonth} ${date.month} ${date.year}"
        }

//    val expenseEntriesByDay: Map<Date, LedgerTransaction>
//        get() {
//            // see notes above
//        }

    // fun getExpenseEntries(start: Long, end: Long = System.CurerntTime): List<LedgerEntry.Income>


    // fun getBalance(start: Long, end: Long = System.CurerntTime): List<LedgerEntry.Income>

    private val maxLedgerId: Int
        get() = ledgerEntries.maxOf { it.value.id }

    fun addIncome(amount: Double) {
        ledgerEntries[System.currentTimeMillis()] = LedgerTransaction.Income(
            id =  maxLedgerId + 1,
            amount = amount,
            created = System.currentTimeMillis()
        )
    }

    fun addExpense(amount: Double) {
        ledgerEntries[System.currentTimeMillis()] = LedgerTransaction.Expense(
            id =  maxLedgerId + 1,
            amount = amount,
            created = System.currentTimeMillis()
        )
    }
}
