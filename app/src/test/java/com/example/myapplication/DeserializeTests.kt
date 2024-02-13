package com.example.myapplication

import com.example.myapplication.dto.LedgerRecordDto
import com.example.myapplication.dto.LedgerTransaction
import com.example.myapplication.dto.asTransaction
import com.example.myapplication.dummydata.testLedgerApiResult
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test

class DeserializeTests {
    private val incomeLedgerDto = """ {
    "id": 0,
    "created_at": 1699898979211,
    "type": "income",
    "amount": 0
  }"""

    private val expenseLedgerDto = """
        {
    "id": 12,
    "created_at": 1699898896865,
    "type": "expense",
    "amount": 200
  }
    """.trimIndent()

    @Test
    fun test_DeserializeToLedger() {
        val sut = Json.decodeFromString<LedgerRecordDto>(incomeLedgerDto)
        Assert.assertNotNull(sut)


        val sut2 = Json.decodeFromString<LedgerRecordDto>(expenseLedgerDto)
        Assert.assertNotNull(sut2)

    }

    @Test
    fun test_DtosToEntity() {
        val sut = Json.decodeFromString<LedgerRecordDto>(incomeLedgerDto).asTransaction
        Assert.assertTrue(sut is LedgerTransaction.Income)

        val sut2 = Json.decodeFromString<LedgerRecordDto>(expenseLedgerDto).asTransaction
        Assert.assertTrue(sut2 is LedgerTransaction.Expense)
    }

    @Test
    fun testDtosInListToEntities() {
        val json = "[ $incomeLedgerDto, $expenseLedgerDto]"
        val sut = Json.decodeFromString<List<LedgerRecordDto>>(json)
        Assert.assertNotNull(sut)

        val entities = sut.map { it.asTransaction }

        Assert.assertTrue(entities.first() is LedgerTransaction.Income)
        Assert.assertTrue(entities.last() is LedgerTransaction.Expense)
    }


    @Test
    fun test_LedgerApiOutput() {
        val sut = Json.decodeFromString<List<LedgerRecordDto>>(testLedgerApiResult)
        Assert.assertNotNull(sut)
        Assert.assertTrue(sut.size > 10) // would normally test actual size
    }
}
