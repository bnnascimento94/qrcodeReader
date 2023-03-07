package com.vullpes.uiloginscreen.data.repository

import com.vullpes.uiloginscreen.data.room.SavedQrcodeDao
import com.vullpes.uiloginscreen.domain.model.SavedQrcode
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch


@RunWith(MockitoJUnitRunner::class)
class QrcodeRepositoryImplTest {

    @Mock
    lateinit var savedQrcodeDao: SavedQrcodeDao
    lateinit var qrcodeRepositoryImpl: QrcodeRepositoryImpl

    @Before
    fun setup(){
        qrcodeRepositoryImpl = QrcodeRepositoryImpl(savedQrcodeDao)
    }


    @Test
    fun `test when user save a qrcode`() = runBlocking{
        `when`(savedQrcodeDao.insert(SavedQrcode(null,"123456"))).thenReturn(1)

        val result = qrcodeRepositoryImpl.saveQrcode("123456")

        assertThat(result.data, `is`(true))
    }

    @Test
    fun `Test when an user delete an item from qrcodeList`() = runBlocking{
        `when`(savedQrcodeDao.selectQrcodeById(1)).thenReturn(null)

        val result = qrcodeRepositoryImpl.deleteQrcode(1)

        assertThat(result.data, `is`(true))
    }

    @Test
    fun `test when all qrcodes is deleted`() = runBlocking{
        `when`(savedQrcodeDao.selectAll()).thenReturn(ArrayList())

        val result = qrcodeRepositoryImpl.deleteAllQrcodes()

        assertThat(result.data, `is`(true))
    }


    @Test
    fun `test when user lists all the qrcodes`() = runBlocking{
        val listQrcodes = listOf(SavedQrcode(1,"123456"))
        `when`(savedQrcodeDao.listAll()).thenReturn(flowOf(listQrcodes))

        val result = qrcodeRepositoryImpl.listAllQrcodes()

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            result.collect { listaQrode ->
                assertThat(listaQrode.size, `is`(1) )
                assertThat(listaQrode.get(0).qrcode, `is`("123456"))
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }



}