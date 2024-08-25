package com.example.enocinterview.core.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FullWidthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Double = 60.0,
    width: Double = 200.0,
    cornerRadius: Int = 8
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 16.dp)
            .width(width.dp)
            .height(height.dp)
        ,
        shape = RoundedCornerShape(cornerRadius.dp)
    ) {
        Text(text)
    }
}