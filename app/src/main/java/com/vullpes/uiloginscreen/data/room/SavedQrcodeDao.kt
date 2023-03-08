package com.vullpes.uiloginscreen.data.room

import androidx.room.*
import com.vullpes.uiloginscreen.domain.model.SavedQrcode
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedQrcodeDao {

    @Insert
    fun insert(savedQrcode: SavedQrcode): Int?

    @Update
    fun update(savedQrcode: SavedQrcode)

    @Delete
    fun delete(savedQrcode: SavedQrcode)

    @Query("delete from saved_qrcode")
    fun deleteAll()

    @Query("delete from saved_qrcode where id =:qrcodeID ")
    fun deleteQrcodeById(qrcodeID: Int)

    @Query("select * from saved_qrcode where id =:qrcodeID ")
    fun selectQrcodeById(qrcodeID: Int): SavedQrcode?

    @Query("select * from saved_qrcode")
    fun selectAll(): List<SavedQrcode>

    @Query("select * from saved_qrcode")
    fun listAll(): Flow<List<SavedQrcode>>

}