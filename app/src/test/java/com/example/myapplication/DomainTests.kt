package com.example.myapplication

import androidx.compose.material3.AssistChip
import com.example.myapplication.dto.LedgerTransaction
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DomainTests {
    private lateinit var sut: LedgerDomain

    @Before
    fun tearUp() {
        sut = LedgerDomain()
    }

    @Test
    fun test_DomainLoads() {
        Assert.assertTrue(sut.ledgerEntries.isNotEmpty())
        Assert.assertEquals(135, sut.ledgerEntries.size) // 135 entries
    }


    @Test
    fun test_AddIncome() {
        Assert.assertEquals(135, sut.ledgerEntries.size)

        sut.addIncome(2_000_001.0)
        Assert.assertEquals(136, sut.ledgerEntries.size)

        val last: LedgerTransaction = sut.ledgerEntries.values.last() ?: throw Exception("Nope")

        Assert.assertEquals(2_000_001.0, last.amount, 0.0)
        Assert.assertTrue(last is LedgerTransaction.Income)
    }

    @Test
    fun test_AddExpense() {
        Assert.assertEquals(135, sut.ledgerEntries.size)

        sut.addExpense(2_000_002.0)
        Assert.assertEquals(136, sut.ledgerEntries.size)

        val last: LedgerTransaction = sut.ledgerEntries.values.last() ?: throw Exception("Nope")

        Assert.assertEquals(2_000_002.0, last.amount, .0)
        Assert.assertTrue(last is LedgerTransaction.Expense)
    }

    @Test
    fun test_LedgerIncomeEntries() {
        val incomes = sut.incomeEntries

        Assert.assertTrue(incomes.size < sut.ledgerEntries.size)

        val expenses = sut.expenseEntries
        Assert.assertTrue(expenses.size < sut.ledgerEntries.size)

        Assert.assertEquals(incomes.size + expenses.size, sut.ledgerEntries.size)
    }
}