package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.components.CalendarButton
import com.example.myapplication.ui.components.LedgerChart
import com.example.myapplication.ui.components.LedgerChartType
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Purple40

/*
@TODO
- Most of these should be separated as their own components
- Add FAB Button and open ADD ENTRY form w/scrim on touch
     - Note, this would then call LedgerDomain.addExpense / LedgerDomain.addIncome and update VM
- Add Splash Screen (cannot export the SVG however)
- Calendar button Spacing is not good - Not 100% sure why, but this is something I need to look into
- Fix status bar color
 */

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme(
                darkTheme = false, dynamicColor = false
            ) {

                Scaffold(topBar = { } ,
                    //floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {},
                            containerColor = Purple40,
                            shape = CircleShape
                        ) {
                            Icon(Icons.Filled.Add,"")
                        }
                    }
                    , content = {
                        MainPage(it)
                    }
                )
            }
        }
    }
}

@Composable
fun MainPage(paddingValues: PaddingValues) {
    val scrollState = rememberScrollState()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(start = 25.dp, end = 27.dp, top = 32.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                CalendarButton(
                    label = "Sep 27, 2023", onClick = {}
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(" -> ", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.size(16.dp))
                CalendarButton(label = "Nov 11, 2023", onClick = {})
            }
            Spacer(modifier = Modifier.size(16.dp))
            LedgerChart(
                legend = LedgerChartType.Balance,
                chartModel = ChartModel(
                    value = 29340.95,
                    entries = mapOf<String, Double> (
                        "Mar" to 184.0,
                        "Apr" to 283.1,
                        "May" to 762.4
                    )
                )
            )

            Spacer(modifier = Modifier.size(16.dp))
            LedgerChart(
                legend = LedgerChartType.Income,
                chartModel = ChartModel(
                    value = 39430.95,
                    entries = mapOf<String, Double> (
                        "Mar" to 184.0,
                        "Apr" to 283.1,
                        "May" to 762.4
                    )
                )
            )

            Spacer(modifier = Modifier.size(16.dp))
            LedgerChart(
                legend = LedgerChartType.Expenses,
                chartModel = ChartModel(
                    value = 10000.00,
                    entries = mapOf<String, Double> (
                        "Mar" to 184.0,
                        "Apr" to 283.1,
                        "May" to 762.4
                    )
                )
            )

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}


