package com.sugarspoon.design_system.buttons

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.sugarspoon.design_system.Dimensions.IconBigLarge
import com.sugarspoon.design_system.Dimensions.IconLarge
import com.sugarspoon.design_system.theme.DarkOverlay
import com.sugarspoon.design_system.theme.LightOverlay

@SuppressLint("UnrememberedAnimatable")
@Composable
fun ButtonIcon(
    modifier: Modifier,
    icon: Int,
    tabName: String,
    selected: Boolean = false,
    useAnimation: Boolean = true,
    onClick: (String) -> Unit
) {
    val selectedColor = if(isSystemInDarkTheme()) DarkOverlay else LightOverlay
    val unSelectedColor = Color.Transparent
    val sizeAnimation = Animatable(0f)

    IconButton(
        onClick = {
            onClick(tabName)
        },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = tabName,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(if(selected) IconBigLarge else IconLarge)
                .drawBehind {
                    drawCircle(
                        color = if (selected) selectedColor else unSelectedColor,
                        radius = if(useAnimation) sizeAnimation.value else RadiusCircle
                    )
                }
        )
    }

    LaunchedEffect(key1 = selected) {
        sizeAnimation.animateTo(RadiusCircle, tween(200))
    }
}

private const val RadiusCircle = 80f