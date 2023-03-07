package com.vullpes.uiloginscreen.domain.repository

import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.model.SavedQrcode
import kotlinx.coroutines.flow.Flow

interface QrcodeRepository {

    suspend fun saveQrcode(qrcode:String): Resource<Boolean>
    suspend fun deleteQrcode(id:Int): Resource<Boolean>
    suspend fun deleteAllQrcodes(): Resource<Boolean>
    fun listAllQrcodes(): Flow<List<SavedQrcode>>
}