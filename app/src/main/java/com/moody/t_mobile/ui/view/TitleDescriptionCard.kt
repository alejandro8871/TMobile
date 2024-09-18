package com.moody.t_mobile.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun TitleDescriptionCard(item: Card) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.card.title?.value ?: "",
                color = Color(
                    android.graphics.Color.parseColor(
                        item.card.title?.attributes?.text_color ?: "#000000"
                    )
                ),
                fontSize = item.card.title?.attributes?.font?.size?.sp ?: 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.card.description?.value ?: "",
                color = Color(
                    android.graphics.Color.parseColor(
                        item.card.description?.attributes?.text_color ?: "#000000"
                    )
                ),
                fontSize = item.card.description?.attributes?.font?.size?.sp ?: 14.sp
            )
        }
    }
}