package com.vullpes.uiloginscreen.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vullpes.uiloginscreen.domain.model.SavedQrcode

@Composable
fun QrcodeListItem(qrcode: SavedQrcode, onItemClick: (SavedQrcode) -> Unit){
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
            .clickable { onItemClick(qrcode) }

        ,
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Qrcode Salvo:",
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.padding(20.dp),
                text = qrcode.qrcode!!,
                style = MaterialTheme.typography.body2,
            )
        }

    }
    
}