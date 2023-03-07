package com.vullpes.uiloginscreen.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_qrcode")
data class SavedQrcode(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var qrcodeID: Int? = null,
    var qrcode: String? = null
)