package com.vullpes.uiloginscreen.presentation.qrcodeScreen

sealed class QrcodeEvent {
    object SaveQrcode: QrcodeEvent()
    data class QrcodeCaptured(val qrcode: String) : QrcodeEvent()
}