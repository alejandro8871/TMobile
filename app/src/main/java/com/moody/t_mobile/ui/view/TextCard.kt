package com.moody.t_mobile.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moody.t_mobile.model.Card

@Composable
fun TextCard(card: Card) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {
        Text(
            text = card.card.value ?: "",
            color = Color(
                android.graphics.Color.parseColor(
                    card.card.attributes?.text_color ?: "#000000"
                )
            ),
            fontSize = card.card.attributes?.font?.size?.sp ?: 16.sp,
            modifier = Modifier.padding(16.dp)
        )
    }

}