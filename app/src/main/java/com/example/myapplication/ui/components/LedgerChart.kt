package com.example.myapplication.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ChartModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.LightText
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.text.NumberFormat

/*
@TODO

-  Chart line colors and other styling to be updated (color is extension of the Legend)
   https://patrykandpatrick.com/vico/api/compose-m3/com.patrykandpatrick.vico.compose.m3.style/m3-chart-style
- Need to wire up "real" data -
 */

@Composable
fun LedgerChart(legend: LedgerChartType, chartModel: ChartModel, modifier: Modifier = Modifier) {
    Card (
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color(0xFF000000)
        )
    ) {
        Column (modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(id = legend.nameResource),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = LightText
                )
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = chartModel.currencyValue, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.size(16.dp))
            Chart(
                chart = columnChart(),
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
                model = chartModel.values,
            )
        }
    }
}

private val ChartModel.currencyValue: String
    get() {
        val format = NumberFormat.getCurrencyInstance().apply{
            maximumFractionDigits = 2
        }
        return format.format(this.value)
    }

private val ChartModel.values: ChartEntryModel
    get() {
        return entryModelOf(
            entriesOf(5f, 10f, 8f, 9f, 12f, 24f, 9f, 14f, 19f)
        )
    }


@Preview
@Composable
fun PreviewChart() {
    val entries = mapOf<String, Double> (
        "Mar" to 184.0,
        "Apr" to 283.1,
        "May" to 762.4
    )
    MyApplicationTheme {
        LedgerChart(LedgerChartType.Income, ChartModel(value = 24000.87, entries = entries))
    }
}


enum class LedgerChartType {
    Balance, Income, Expenses
}

private val LedgerChartType.nameResource: Int
    @StringRes get() = when (this) {
        LedgerChartType.Balance -> R.string.ledger_balance
        LedgerChartType.Expenses -> R.string.ledger_expenses
        LedgerChartType.Income -> R.string.ledger_income
    }

private val LedgerChartType.lineColor: Color
    get() = when (this) {
        LedgerChartType.Balance -> Color(0xFF748AFB)
        LedgerChartType.Expenses -> Color(0xFFFFB661)
        LedgerChartType.Income -> Color(0xFFFF539B)
    }
