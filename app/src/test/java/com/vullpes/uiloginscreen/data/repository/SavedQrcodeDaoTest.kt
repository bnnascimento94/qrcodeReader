package com.vullpes.uiloginscreen.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vullpes.uiloginscreen.data.room.RoomService
import com.vullpes.uiloginscreen.data.room.SavedQrcodeDao
import com.vullpes.uiloginscreen.domain.model.SavedQrcode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class SavedQrcodeDaoTest {

    private lateinit var dataBase: RoomService
    private lateinit var savedQrcodeDao: SavedQrcodeDao

    @Before
    fun setup(){
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomService::class.java
        ).allowMainThreadQueries().build()

        savedQrcodeDao = dataBase.savedQrcodeDao()
    }

    @After
    fun tearDown(){
        dataBase.close()
    }


    @Test
    fun `test saving a new qrcode`() = runBlocking {
        val qrcodeSaved = SavedQrcode(null,"123456")
        savedQrcodeDao.insert(qrcodeSaved)

        assertThat(savedQrcodeDao.selectAll().size, `is`(1))
    }

    @Test
    fun `test updating qrcode`() = runBlocking {
        var qrcodeSaved: SavedQrcode? = SavedQrcode(null,"123456")
        val id: Int? = savedQrcodeDao.insert(qrcodeSaved!!)?.toInt()

        id?.let {
            qrcodeSaved = savedQrcodeDao.selectQrcodeById(it)
        }

        savedQrcodeDao.update(qrcodeSaved!!.copy(qrcode = "7890"))

        val qrcodeUpdated = savedQrcodeDao.selectQrcodeById(id!!)

        assertThat(qrcodeUpdated!!.qrcode, `is`(not(equals(qrcodeSaved!!.qrcode))))

    }

    @Test
    fun `delete a qrcode saved when given an ID`(){
        var qrcodeSaved: SavedQrcode? = SavedQrcode(null,"123456")
        val id: Int? = savedQrcodeDao.insert(qrcodeSaved!!)?.toInt()

        savedQrcodeDao.deleteQrcodeById(id!!)

        qrcodeSaved = savedQrcodeDao.selectQrcodeById(id)

        assertThat(qrcodeSaved, `is`(nullValue()))
    }

    @Test
    fun `delete all qrcodes`(){
        var qrcodeSaved: SavedQrcode? = SavedQrcode(null,"123456")
        val id: Int? = savedQrcodeDao.insert(qrcodeSaved!!)?.toInt()

        savedQrcodeDao.deleteAll()

        qrcodeSaved = savedQrcodeDao.selectQrcodeById(id!!)

        assertThat(qrcodeSaved, `is`(nullValue()))
    }



    @Test
    fun `test listing all qrcodes`() = runBlocking {
        val qrcodeSaved = SavedQrcode(null,"123456")
        savedQrcodeDao.insert(qrcodeSaved)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            savedQrcodeDao.listAll().collect { listaQrode ->
                assertThat(listaQrode.size, `is`(1) )
                assertThat(listaQrode.get(0).qrcode, `is`("123456"))
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

}