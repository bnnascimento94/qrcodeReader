package com.vullpes.uiloginscreen.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Login(
    @PrimaryKey(autoGenerate = true)
    var loginId: Int? = null,
    var nome:String?= null,
    var email:String? = null,
    var senha:String? = null
)