package com.moody.t_mobile.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.moody.t_mobile.model.Card

@Composable
fun ImageTitleDescriptionCard(item: Card) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val imageWidth = item.card.image?.size?.width ?: 1  // Avoid division by zero
    val imageHeight = item.card.image?.size?.height ?: 1

    val imageAspectRatio = imageHeight.toFloat() / imageWidth.toFloat()
    val dynamicHeight = screenWidth * imageAspectRatio
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box {
            // Image
            Image(
                painter = rememberAsyncImagePainter(item.card.image?.url),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dynamicHeight)
            )

            // Title and Description at the bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.2f)) // Semi-transparent background for readability
                    .padding(16.dp)
            ) {
                Text(
                    text = item.card.title?.value ?: "",
                    color = Color(
                        android.graphics.Color.parseColor(
                            item.card.title?.attributes?.text_color ?: "#FFFFFF"
                        )
                    ),
                    fontSize = item.card.title?.attributes?.font?.size?.sp ?: 15.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.card.description?.value ?: "",
                    color = Color(
                        android.graphics.Color.parseColor(
                            item.card.description?.attributes?.text_color ?: "#FFFFFF"
                        )
                    ),
                    fontSize = item.card.description?.attributes?.font?.size?.sp ?: 14.sp
                )
            }
        }
    }
}