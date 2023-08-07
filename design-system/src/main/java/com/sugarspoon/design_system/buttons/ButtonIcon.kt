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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
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
    contentDescription: String? = null,
    selected: Boolean = false,
    useAnimation: Boolean = true,
    iconColor: Color? = null,
    onClick: (String) -> Unit
) {
    val selectedColor = if (isSystemInDarkTheme()) DarkOverlay else LightOverlay
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
            contentDescription = contentDescription ?: tabName,
            tint = iconColor ?: MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(if (selected) IconBigLarge else IconLarge)
                .drawBehind {
                    drawCircle(
                        color = if (selected) selectedColor else unSelectedColor,
                        radius = if (useAnimation) sizeAnimation.value else RadiusCircle
                    )
                }
        )
    }

    LaunchedEffect(key1 = sizeAnimation) {
        sizeAnimation.animateTo(RadiusCircle, tween(200))
    }
}

@SuppressLint("UnrememberedAnimatable")
@Composable
fun MenuButton(
    modifier: Modifier,
    icon: Int,
    tabName: String,
    isSelected: Boolean = false,
    useAnimation: Boolean = true,
    iconColor: Color? = null,
    onClick: (String) -> Unit
) {
    val selectedColor = if (isSystemInDarkTheme()) DarkOverlay else LightOverlay
    val unSelectedColor = Color.Transparent
    val sizeAnimation = Animatable(0f)

    IconButton(
        onClick = {
            onClick(tabName)
        },
        modifier = modifier.semantics {
            this.contentDescription = tabName
            selected = isSelected
            role = Role.RadioButton
        }
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(if (isSelected) IconBigLarge else IconLarge)
                .drawBehind {
                    drawCircle(
                        color = if (isSelected) selectedColor else unSelectedColor,
                        radius = if (useAnimation) sizeAnimation.value else RadiusCircle
                    )
                }
        )
    }

    LaunchedEffect(key1 = sizeAnimation) {
        sizeAnimation.animateTo(RadiusCircle, tween(200))
    }
}

private const val RadiusCircle = 80f