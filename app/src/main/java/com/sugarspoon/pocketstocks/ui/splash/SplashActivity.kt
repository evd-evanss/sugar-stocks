package com.sugarspoon.pocketstocks.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sugarspoon.design_system.Dimensions.bottomSpacing
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.design_system.theme.DesignSystemTheme
import com.sugarspoon.pocketfinance.R
import com.sugarspoon.pocketstocks.ui.main.MainActivity
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.splash),
            )

            val progress by animateLottieCompositionAsState(composition, speed = 2f)
            val scale = remember { Animatable(initialValue = 0f) }

            DesignSystemTheme(true) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    if (progress >= .8f) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .scale(scale.value)
                                .wrapContentSize()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bull),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(150.dp)
                                    .topSpacing(),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            SugarText(
                                modifier = Modifier
                                    .inlineSpacingMedium()
                                    .topSpacing()
                                    .bottomSpacing(),
                                text = stringResource(id = R.string.app_name),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    lineBreak = LineBreak.Heading,
                                    fontSize = 32.sp
                                )
                            )
                            LaunchedEffect(key1 = true) {
                                scale.animateTo(
                                    targetValue = .8f,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = {
                                            OvershootInterpolator(5f).getInterpolation(it)
                                        }
                                    ),
                                )
                                delay(2000L)
                                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                                finish()
                            }
                        }
                    } else {
                        LottieAnimation(
                            composition = composition,
                            progress = { progress },
                            modifier = Modifier,
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun LootiePreview() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash),
    )

    val progress by animateLottieCompositionAsState(composition, speed = 2f)
    DesignSystemTheme(true) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            if (progress >= .8f) {
                Column {
                    SugarText(
                        modifier = Modifier.inlineSpacingMedium(),
                        text = stringResource(id = R.string.app_name),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            lineBreak = LineBreak.Heading
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_bull),
                        contentDescription = null
                    )
                }
            } else {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier,
                )
            }
        }

    }
}
