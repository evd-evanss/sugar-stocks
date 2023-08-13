package com.sugarspoon.design_system.lists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.inlineSpacingSmall
import com.sugarspoon.design_system.Dimensions.topSpacing
import com.sugarspoon.design_system.R
import com.sugarspoon.design_system.text.SugarText
import com.sugarspoon.design_system.icons.SugarSpoonIcons
import com.sugarspoon.design_system.theme.SkeletonColor

object SugarLists {

    @Composable
    fun TopStockItem(
        logo: String,
        name: String,
        code: String,
        change: String,
        sector: String,
        background: Color = MaterialTheme.colorScheme.background,
        modifier: Modifier,
        isLoading: Boolean = false,
        onClick: (String, String) -> Unit
    ) {
        Card(
            modifier = modifier.clickable {
                onClick(code, logo)
            },
            shape = Dimensions.ListItemShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = Dimensions.Level2
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            border = BorderStroke(
                width = Dimensions.BorderStrokeMedium,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .width(ItemWidth)
                        .background(background)
                        .padding(Dimensions.SpacingSmall)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(logo)
                                .decoderFactory(SvgDecoder.Factory())
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .inlineSpacingSmall()
                                .skeleton(isLoading),
                            contentScale = ContentScale.Inside
                        )
                        Column(
                            modifier = Modifier
                                .skeleton(isLoading)
                        ) {
                            SugarText(
                                modifier = Modifier,
                                text = name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                SugarText(
                                    modifier = Modifier.padding(end = Dimensions.SpacingVerySmall),
                                    text = "Código:",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                SugarText(
                                    modifier = Modifier,
                                    text = code,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                SugarText(
                                    modifier = Modifier.padding(end = Dimensions.SpacingVerySmall),
                                    text = "Setor:",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                SugarText(
                                    modifier = Modifier,
                                    text = sector,
                                    style = MaterialTheme.typography.bodyLarge,
                                    maxLines = 1
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                SugarText(
                                    modifier = Modifier.padding(end = Dimensions.SpacingVerySmall),
                                    text = "Variação máxima (dia):",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                SugarText(
                                    modifier = Modifier,
                                    text = change,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    @Composable
    fun StockItem(
        logo: String,
        name: String,
        code: String,
        sector: String,
        background: Color = MaterialTheme.colorScheme.background,
        modifier: Modifier,
        isLoading: Boolean = false,
        onClick: (String, String) -> Unit,
    ) {
        Card(
            modifier = modifier.clickable {
                onClick(code, logo)
            },
            shape = Dimensions.ListItemShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = Dimensions.Level2
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            border = BorderStroke(
                width = Dimensions.BorderStrokeMedium,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background)
                        .padding(Dimensions.SpacingSmall)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(logo)
                                .decoderFactory(SvgDecoder.Factory())
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .inlineSpacingSmall()
                                .skeleton(isLoading),
                            contentScale = ContentScale.Inside
                        )
                        Column(
                            modifier = Modifier
                                .skeleton(isLoading)
                        ) {
                            SugarText(
                                modifier = Modifier,
                                text = name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                SugarText(
                                    modifier = Modifier.padding(end = Dimensions.SpacingVerySmall),
                                    text = "Código:",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                SugarText(
                                    modifier = Modifier,
                                    text = code,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                SugarText(
                                    modifier = Modifier.padding(end = Dimensions.SpacingVerySmall),
                                    text = "Setor:",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                SugarText(
                                    modifier = Modifier,
                                    text = sector,
                                    style = MaterialTheme.typography.bodyLarge,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
                Icon(
                    painter = painterResource(id = SugarSpoonIcons.Outline.ArrowRight),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .topSpacing()
                        .inlineSpacingMedium()
                        .height(Dimensions.IconLarge),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }

    @Composable
    fun SimpleStockItem(
        logo: String,
        name: String,
        code: String,
        background: Color = MaterialTheme.colorScheme.background,
        modifier: Modifier,
        isLoading: Boolean = false,
        onClick: (String, String) -> Unit,
        onClickUnfollow: (String) -> Unit,
    ) {
        Card(
            modifier = modifier.clickable {
                onClick(code, logo)
            },
            shape = Dimensions.ListItemShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = Dimensions.Level2
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            border = BorderStroke(
                width = Dimensions.BorderStrokeMedium,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background)
                    .padding(Dimensions.SpacingSmall)
            ) {
                Row(
                    modifier = Modifier
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(logo)
                            .decoderFactory(SvgDecoder.Factory())
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .inlineSpacingSmall()
                            .skeleton(isLoading),
                        contentScale = ContentScale.Inside
                    )
                    Column(
                        modifier = Modifier
                            .skeleton(isLoading)
                    ) {
                        SugarText(
                            modifier = Modifier.padding(end = Dimensions.SpacingVerySmall),
                            text = name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            ),
                            maxLines = 1
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SugarText(
                                modifier = Modifier.padding(end = Dimensions.SpacingVerySmall),
                                text = "Código:",
                                style = MaterialTheme.typography.titleMedium
                            )
                            SugarText(
                                modifier = Modifier.weight(1f),
                                text = code,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                IconButton(
                    onClick = { onClickUnfollow(code) },
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Icon(
                        painter = painterResource(id = SugarSpoonIcons.Outline.Trash2),
                        contentDescription = null
                    )
                }
            }
        }
    }

    @Composable
    fun MarketB3Item(
        date: String,
        points: String,
        status: String,
        isOpened: Boolean,
        change: String,
        regularMarketChange: Number,
        background: Color = MaterialTheme.colorScheme.background,
        modifier: Modifier,
        isLoading: Boolean = false
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.skeleton(isLoading),
        ) {
            Column(
                modifier = Modifier
                    .background(background)
            ) {
                SugarText(
                    modifier = Modifier
                        .inlineSpacingMedium()
                        .skeleton(isLoading),
                    text = "IBOVESPA - $points pts",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                SugarText(
                    modifier = Modifier.inlineSpacingMedium(),
                    text = status,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = getMarketStatusColor(isOpened)
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SugarText(
                        modifier = Modifier
                            .padding(start = Dimensions.SpacingMedium)
                            .skeleton(isLoading),
                        text = change,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = getStatusColor(regularMarketChange)
                        )
                    )
                    Icon(
                        painter = painterResource(id = getIcon(regularMarketChange)),
                        contentDescription = null,
                        tint = getStatusColor(regularMarketChange)
                    )
                }
                SugarText(
                    modifier = Modifier
                        .inlineSpacingMedium()
                        .skeleton(isLoading),
                    text = "Atualizado em: $date",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
            Box(
                modifier = Modifier
                    .size(55.dp)
                    .drawBehind {
                        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0f, 130f),
                            end = Offset(size.width, 130f),
                            pathEffect = pathEffect,
                            strokeWidth = 5f
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_timeline),
                    contentDescription = null,
                    tint = getStatusColor(regularMarketChange),
                    modifier = Modifier
                        .size(50.dp)
                        .rotateIcon(regularMarketChange)
                )
            }
        }
    }

    @Composable
    fun InflationItem(
        date: String,
        value: String,
        background: Color = MaterialTheme.colorScheme.background,
        modifier: Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            Column(
                modifier = Modifier
                    .background(background)
            ) {
                SugarText(
                    modifier = Modifier
                        .inlineSpacingMedium(),
                    text = "INFLAÇÃO - ($value%)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                SugarText(
                    modifier = Modifier
                        .inlineSpacingMedium(),
                    text = "Atualizado em: $date",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}

@Composable
private fun getStatusColor(number: Number) =
    if (number.toDouble() < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

private fun Modifier.rotateIcon(number: Number) = composed {
    rotate(if (number.toDouble() < 0) 20f else 0f)
}

@Composable
private fun getMarketStatusColor(isOpened: Boolean) =
    if (isOpened) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

@Composable
private fun getIcon(number: Number) =
    if (number.toDouble() < 0) SugarSpoonIcons.Outline.ArrowBottom1 else SugarSpoonIcons.Outline.ArrowTop2

fun Modifier.skeleton(isVisible: Boolean, shape: Shape = Dimensions.CardShape) = composed {
    if (isVisible) padding(top = 16.dp, bottom = 16.dp)
    else Modifier
    placeholder(
        visible = isVisible,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = shape,
        highlight = PlaceholderHighlight.shimmer(
            highlightColor =SkeletonColor
        )
    )
}

private val ItemWidth = 280.dp