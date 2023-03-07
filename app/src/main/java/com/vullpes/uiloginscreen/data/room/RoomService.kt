package com.vullpes.uiloginscreen.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vullpes.uiloginscreen.domain.model.Login
import com.vullpes.uiloginscreen.domain.model.SavedQrcode

@Database(
    entities = [Login::class, SavedQrcode::class],
    version = 1, exportSchema = false
)
abstract class RoomService : RoomDatabase(){
    abstract fun loginDao(): LoginDao
    abstract fun savedQrcodeDao(): SavedQrcodeDao

    companion object{
        const val DATABASE_NAME = "qrcode_db"
    }

}