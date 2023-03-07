package com.vullpes.uiloginscreen.data.repository

import com.vullpes.uiloginscreen.data.room.SavedQrcodeDao
import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.model.SavedQrcode
import com.vullpes.uiloginscreen.domain.repository.QrcodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QrcodeRepositoryImpl @Inject constructor(private val savedQrcodeDao: SavedQrcodeDao): QrcodeRepository {
    override suspend fun saveQrcode(qrcode: String): Resource<Boolean> {
        return try{
            val q = SavedQrcode(null,qrcode)
            val id : Long? = savedQrcodeDao.insert(q)

            if(id != null && id > 0 ){
                Resource.Success(true)
            }else{
                Resource.Success(false)
            }
        }catch (e:Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteQrcode(id: Int): Resource<Boolean> {
        return try{
            savedQrcodeDao.deleteQrcodeById(id)
            if(savedQrcodeDao.selectQrcodeById(id) == null){
                Resource.Success(true)
            }else{
                Resource.Success(false)
            }
        }catch (e:Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteAllQrcodes(): Resource<Boolean> {
        return try{
            savedQrcodeDao.deleteAll()
            if(savedQrcodeDao.selectAll().isEmpty()){
                Resource.Success(true)
            }else{
                Resource.Success(false)
            }
        }catch (e:Exception){
            Resource.Error(e.message)
        }
    }

    override  fun listAllQrcodes(): Flow<List<SavedQrcode>> {
        return savedQrcodeDao.listAll()
    }
}