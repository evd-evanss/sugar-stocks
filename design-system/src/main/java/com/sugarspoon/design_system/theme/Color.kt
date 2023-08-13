package com.sugarspoon.design_system.theme

import androidx.compose.ui.graphics.Color

val TeaGreen = Color(0xFFc9f2c7)
val Olivine = Color(0xFF96be8c)
val Asparagus = Color(0xFF629460)

val LightGreen = Color(0xFFaceca1)
val DarkGreen = Color(0xFF629460)
val LightOverlay = Color(0x31F0EDED)
val DarkOverlay = Color(0x31383838)
val GrayLight = Color(0xFFF1F1F1)
val Dark01 = Color(0xFF322F38)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceDark = Color(0xFF4B4949)

val BloodLight = Color(0xFFF55555)
val BackgroundLight = Color(0xFFF8F9FA)
val BackgroundDark = Color(0xFF002236)

val SkeletonColor = GrayLight
val StocksColor: (Boolean) -> Color = { if(it) Color(0xFFB8C2FF) else Color(0xFF3949AB) }
val FixedIncomeColor: (Boolean) -> Color = { if(it) Color(0xFFA6EBF3) else Color(0xFF00ACC1) }
val CambialColor: (Boolean) -> Color = { if (it)Color(0xFFF9FDB6) else Color(0xFFC0CA33)}
val MultiMarketColor: (Boolean) -> Color = { if(it) Color(0xFFF0A48C) else Color(0xFFF4511E)}
val ForesightColor: (Boolean) -> Color = { if(it) Color(0xFFDDB1A3) else Color(0xFF81594C) }