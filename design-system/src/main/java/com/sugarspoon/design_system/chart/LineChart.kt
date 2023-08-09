package com.sugarspoon.design_system.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.chart.models.DataPoint
import com.sugarspoon.design_system.text.SugarText
import java.util.Locale

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    data: List<DataPoint>,
    graphColor: Color,
    showDashedLine: Boolean,
) {
    val textColor = MaterialTheme.colorScheme.onBackground
    if (data.isEmpty()) {
        return
    }
    val spacing = 0f
    val transparentGraphColor = remember(key1 = graphColor) {
        graphColor.copy(alpha = 0.5f)
    }

    val (lowerValue, upperValue) = remember(key1 = data) {
        Pair(
            data.minBy { it.y },
            data.maxBy { it.y }
        )
    }

    val density = LocalDensity.current
    Column(modifier) {
        SugarText(
            modifier = Modifier
                .align(Alignment.End)
                .inlineSpacingMedium(),
            text = "MAX ${upperValue.yLabel}",
            style = MaterialTheme.typography.labelSmall.copy(
                color = textColor
            )
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .drawBehind {
                    val textPaint = android.graphics
                        .Paint()
                        .apply {
                            color = textColor.toArgb()
                            this.textSize = 22f
                        }

                    val spacePerHour = (size.width - spacing) / data.size
                    val priceStep = (upperValue.y - lowerValue.y) / 5f
                    (0..4).forEach { i ->
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                (lowerValue.y + priceStep * i).formatWithDecimalPlaces(),
                                size.width - 40f,
                                size.height - spacing - i * size.height / 5f,
                                textPaint
                            )
                        }
                    }

                    var lastX = 0f
                    var firstY = 0f
                    val strokePath = Path().apply {
                        val height = size.height
                        for (i in data.indices) {
                            val info = data[i]
                            val nextInfo = data.getOrNull(i + 1) ?: data.last()
                            val leftRatio = (info.y - lowerValue.y) / (upperValue.y - lowerValue.y)
                            val rightRatio =
                                (nextInfo.y - lowerValue.y) / (upperValue.y - lowerValue.y)

                            val x1 = spacing + i * spacePerHour
                            val y1 = height - spacing - (leftRatio * height).toFloat()

                            if (i == 0) {
                                firstY = y1
                            }

                            val x2 = spacing + (i + 1) * spacePerHour
                            val y2 = height - spacing - (rightRatio * height).toFloat()
                            if (i == 0) {
                                moveTo(x1, y1)
                            }
                            lastX = (x1 + x2) / 2f
                            quadraticBezierTo(
                                x1, y1, lastX, (y1 + y2) / 2f
                            )
                        }
                    }

                    val fillPath = android.graphics
                        .Path(strokePath.asAndroidPath())
                        .asComposePath()
                        .apply {
                            lineTo(lastX, size.height - spacing)
                            lineTo(spacing, size.height - spacing)
                            close()
                        }

                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                transparentGraphColor,
                                Color.Transparent
                            ),
                            endY = size.height - spacing
                        ),
                    )

                    drawPath(
                        path = strokePath,
                        color = graphColor,
                        style = Stroke(
                            width = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )

                    if (showDashedLine) {
                        val dottedPath = Path().apply {
                            moveTo(0f, firstY)
                            lineTo(lastX, firstY)
                        }

                        drawPath(
                            path = dottedPath,
                            color = graphColor.copy(alpha = .8f),
                            style = Stroke(
                                width = 1.5.dp.toPx(),
                                cap = StrokeCap.Round,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f), 0f)
                            )
                        )
                    }
                }
        )
        SugarText(
            modifier = Modifier
                .align(Alignment.End)
                .inlineSpacingMedium(),
            text = "MIN ${lowerValue.yLabel}",
            style = MaterialTheme.typography.labelSmall.copy(
                color = textColor
            )
        )
    }
}

fun Double.formatWithDecimalPlaces(places: Int = 3): String {
    return String.format(locale = Locale.getDefault(), format = "%.${places}f", this)
}