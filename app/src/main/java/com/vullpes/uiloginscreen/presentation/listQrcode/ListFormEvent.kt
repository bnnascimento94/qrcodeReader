package com.vullpes.uiloginscreen.presentation.listQrcode

import com.vullpes.uiloginscreen.presentation.login.LoginFormEvent

sealed class ListFormEvent {
    data class DeleteQrcodeById(val id: Int?) : ListFormEvent()
    object DeleteAllQrcodes: ListFormEvent()

}