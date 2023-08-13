package com.sugarspoon.design_system.lists

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.Dimensions.inlineSpacingMedium
import com.sugarspoon.design_system.Dimensions.topSpacingSmall

fun sugarSkeleton(
    isLoading: Boolean,
    lazyListScope: LazyListScope,
    content: () -> Unit,
    count: Int,
) = lazyListScope.run {
    if (isLoading) {
        items(
            count = count,
            itemContent = {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .inlineSpacingMedium()
                        .topSpacingSmall()
                        .skeleton(true, shape = Dimensions.ListItemShape)
                )
            },
        )
    } else {
        content()
    }
}