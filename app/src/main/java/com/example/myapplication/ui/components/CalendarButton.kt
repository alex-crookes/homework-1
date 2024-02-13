package com.example.myapplication.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.ButtonDefaultFill
import com.example.myapplication.ui.theme.ButtonDefaultText
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun CalendarButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            contentColor = ButtonDefaultText,
            containerColor = ButtonDefaultFill,
        )
    ) {
        Text(text = label)

    }
}


@Preview
@Composable
fun CalendarButtonPreview() {
    MyApplicationTheme {
        CalendarButton(label = "Sep 27, 2023") {
            // nothing

        }
    }
}

