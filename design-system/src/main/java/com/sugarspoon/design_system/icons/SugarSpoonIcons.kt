package com.sugarspoon.design_system.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource

object SugarSpoonIcons {

    val Outline: Assets = IconsOutline()
    // todo val Filled: Assets = IconsFilled()

    @Composable
    fun Int.toPainter() = painterResource(this@toPainter)

    @Composable
    fun Int.toImageVector() = ImageVector.vectorResource(this@toImageVector)

    val ImageVectorIcon: @Composable () (Int) -> ImageVector = {
        ImageVector.vectorResource(it)
    }

    val PainterIcon: @Composable () (Int) -> Painter = {
        painterResource(it)
    }
}