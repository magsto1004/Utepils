package com.example.utepils.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.utepils.R

// Set of Material typography styles to start with
val choco = FontFamily(
    Font(R.font.chococrunch)
)

val sfPro = FontFamily(
    Font(R.font.sfprodisplabold, FontWeight.Bold),
    Font(R.font.sfprotextregular, FontWeight.Normal)
)

val AppType = Typography(
    h1 = TextStyle(
        fontFamily = choco,
        fontSize = 25.sp
    ),
    h2 = TextStyle(
        fontFamily = sfPro,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp
    ),
    h3 = TextStyle(
        fontFamily = sfPro,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp
    ),
    h4 = TextStyle(
        fontFamily = sfPro,
        fontSize = 22.sp
    ),

    body1 = TextStyle(
        fontFamily = sfPro,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    overline = TextStyle(
        fontFamily = sfPro,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp
    )
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )




    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)