package com.sugarspoon.design_system.chart
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions

@Composable
fun ChartBar(
    modifier: Modifier,
    entries: List<ChartEntry>,
    limit: Float,
    background: Color = MaterialTheme.colorScheme.background,
    enableAnimation: Boolean = true,
    stroke: Dp = BarHeight,
    strokeCap: StrokeCap = StrokeCap.Round
) {
    val animationProgress = remember {
        Animatable(0f)
    }
    val screenWidth = LocalConfiguration.current.screenWidthDp

    LaunchedEffect(key1 = true) {
        animationProgress.animateTo(screenWidth.toFloat(), tween(2500))
    }
    val cardShape = if(strokeCap == StrokeCap.Round) {
        RoundedCornerShape(8.dp)
    } else {
        RectangleShape
    }

    val canvasModifier = if (enableAnimation) {
        Modifier.width(width = animationProgress.value.dp)
    } else {
        Modifier.fillMaxWidth()
    }
    Card(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        shape = cardShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimensions.Level2
        )
    ) {
        Column(Modifier.background(MaterialTheme.colorScheme.surface).padding(bottom = SmallPadding)) {
            Box(
                modifier = Modifier.semantics {
                    contentDescription = "Barra gráfica"
                }
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(DefaultPadding)
                ) {
                    val factorDiv = (size.width / limit)
                    drawBackground(
                        background = background,
                        width = limit * factorDiv,
                        height = stroke,
                        strokeCap = strokeCap
                    )
                }

                Canvas(
                    modifier = canvasModifier.padding(DefaultPadding)
                ) {
                    val factorDiv = (size.width / limit)
                    entries.forEach { entry ->
                        val xPos = when {
                            entry.value > limit -> limit * factorDiv
                            entry.value <= limit -> entry.value * factorDiv
                            else -> 0f * factorDiv
                        }
                        if (xPos > 0f) {
                            drawBar(
                                background = entry.background,
                                width = xPos,
                                height = stroke,
                                strokeCap = strokeCap
                            )
                        }
                    }
                }
            }
            entries.forEach {
                Row(
                    modifier = Modifier.inlinePadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Canvas(modifier = Modifier) {
                        if(strokeCap == StrokeCap.Round) {
                            drawCircle(
                                radius = 6.dp.toPx(),
                                color = it.background
                            )
                        } else {
                            drawRect(
                                size = Size(width = 12.dp.toPx(), height = 12.dp.toPx()),
                                color = it.background,
                                topLeft = Offset(x = -18f, y = -20f)
                            )
                        }
                    }
                    Text(
                        text = "${it.tag}: ${it.value}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 16.dp),
                    )
                }
            }
            Row(
                modifier = Modifier.inlinePadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(modifier = Modifier) {
                    if(strokeCap == StrokeCap.Round) {
                        drawCircle(
                            radius = 6.dp.toPx(),
                            color = background
                        )
                    } else {
                        drawRect(
                            size = Size(width = 12.dp.toPx(), height = 12.dp.toPx()),
                            color = background,
                            topLeft = Offset(x = -18f, y = -20f)
                        )
                    }
                }
                Text(
                    text = "Limite: $limit",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        }
    }
}

private fun DrawScope.drawBackground(
    width: Float,
    background: Color,
    height: Dp,
    strokeCap: StrokeCap
) {
    drawLine(
        color = background,
        start = Offset(0f, center.y),
        end = Offset(width, center.y),
        strokeWidth = height.toPx(),
        cap = strokeCap
    )
}

private fun DrawScope.drawBar(
    background: Color,
    width: Float,
    height: Dp,
    strokeCap: StrokeCap
) {
    drawLine(
        color = background,
        start = Offset(0f, center.y),
        end = Offset(width, center.y),
        strokeWidth = (height - 1.dp).toPx(),
        cap = strokeCap
    )
}

private val BarHeight = 12.dp
private val DefaultPadding = 16.dp
private val SmallPadding = 8.dp
private fun Modifier.inlinePadding() = composed {
    padding(start = DefaultPadding, end = DefaultPadding)
}