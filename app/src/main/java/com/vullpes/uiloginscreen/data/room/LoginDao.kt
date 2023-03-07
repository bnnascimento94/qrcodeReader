package com.vullpes.uiloginscreen.data.room

import androidx.room.*
import com.vullpes.uiloginscreen.domain.model.Login
import com.vullpes.uiloginscreen.domain.model.SavedQrcode

@Dao
interface LoginDao {

    @Insert
    fun insert(login: Login)

    @Update
    fun update(login: Login)

    @Delete
    fun delete(login: Login)

    @Query("delete from login")
    fun deleteAll()

    @Query("select * from login where email =:email and senha =:senha")
    fun checarLogin(email:String, senha:String): Login?


}