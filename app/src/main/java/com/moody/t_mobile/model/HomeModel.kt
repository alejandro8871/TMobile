package com.moody.t_mobile.model


data class Page(
    val page: CardsList
)

data class CardsList(
    val cards: List<Card>
)

data class Card(
    val card_type: String,
    val card: CardContent
)

data class CardContent(
    val value: String? = null,
    val attributes: CardAttributes? = null,
    val title: CardText? = null,
    val description: CardText? = null,
    val image: CardImage? = null
)

data class CardText(
    val value: String,
    val attributes: CardAttributes
)

data class CardAttributes(
    val text_color: String,
    val font: CardFont
)

data class CardFont(
    val size: Int
)

data class CardImage(
    val url: String,
    val size: ImageSize
)

data class ImageSize(
    val width: Int,
    val height: Int
)