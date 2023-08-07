package com.sugarspoon.design_system

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

object Dimensions {

    val SpacingMedium = 16.dp
    val SpacingSmall = 8.dp
    val SpacingVerySmall = 4.dp
    val None = 0.dp

    //Icons
    val IconBigLarge = 38.dp
    val IconLarge = 32.dp
    val IconMedium = 24.dp
    val IconSmall = 16.dp

    fun Modifier.inlineSpacingMedium() = composed {
        padding(
            start = SpacingMedium,
            end = SpacingMedium
        )
    }

    fun Modifier.endSpacingMedium() = composed {
        padding(
            end = SpacingMedium
        )
    }

    fun Modifier.inlineSpacingSmall() = composed {
        padding(
            start = SpacingSmall,
            end = SpacingSmall
        )
    }

    fun Modifier.inlineSpacingVerySmall() = composed {
        padding(
            start = SpacingVerySmall,
            end = SpacingVerySmall
        )
    }

    fun Modifier.stackSpacingMedium() = composed {
        padding(
            top = SpacingMedium,
            bottom = SpacingMedium
        )
    }

    fun Modifier.topSpacing() = composed {
        padding(
            top = SpacingMedium
        )
    }

    fun Modifier.topSpacingSmall() = composed {
        padding(
            top = SpacingSmall
        )
    }

    fun Modifier.topSpacingVerySmall() = composed {
        padding(
            top = SpacingVerySmall
        )
    }

    fun Modifier.bottomSpacing() = composed {
        padding(
            bottom = SpacingMedium,
        )
    }

    fun Modifier.bottomSpacingVerySmall() = composed {
        padding(
            bottom = SpacingVerySmall,
        )
    }

    val CardShape = RoundedCornerShape(12.dp)
    val ListItemShape = RoundedCornerShape(8.dp)

    // Elevation
    val Level1 = 2.dp
    val Level2 = 4.dp
    val Level3 = 6.dp

    //Border
    val BorderStrokeSmall = .5.dp
    val BorderStrokeMedium = 1.dp
    val BorderStrokeLarge = 2.dp
}