package com.vullpes.uiloginscreen.presentation.listQrcode

import com.vullpes.uiloginscreen.domain.model.SavedQrcode

data class ListQrcodeState (
    val isloading:Boolean = false,
    val qrcodes: List<SavedQrcode> = emptyList(),
    val error: String = ""
)