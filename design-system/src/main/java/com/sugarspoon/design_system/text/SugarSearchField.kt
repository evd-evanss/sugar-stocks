package com.sugarspoon.design_system.text

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sugarspoon.design_system.Dimensions
import com.sugarspoon.design_system.icons.SugarSpoonIcons

@Composable
fun SugarSearchField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    textSize: TextUnit = 16.sp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    maxLines: Int = 1,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClose: () -> Unit,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        textStyle = textStyle.copy(fontSize = textSize, lineHeight = 16.sp,
            baselineShift = BaselineShift.None),
        placeholder = {
            SugarText(
                modifier = Modifier,
                text = label,
                style = textStyle.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = textSize,
                    lineHeight = 16.sp
                )
            )
        },
        maxLines = maxLines,
        singleLine = singleLine,
        readOnly = readOnly,
        interactionSource = interactionSource,
        enabled = isEnabled,
        trailingIcon = {
            val icon = if(text.isEmpty()) SugarSpoonIcons.Outline.Search else SugarSpoonIcons.Outline.Close
            IconButton(onClick = onClose) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Fechar"
                )
            }
        },
        shape = Dimensions.ListItemShape,
        keyboardActions = KeyboardActions(onSearch = { onClose() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )
}