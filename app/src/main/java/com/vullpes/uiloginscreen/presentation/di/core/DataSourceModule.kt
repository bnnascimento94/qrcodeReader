package com.vullpes.uiloginscreen.presentation.di.core

import android.app.Application
import androidx.room.Room
import com.vullpes.uiloginscreen.data.room.RoomService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideDataBase(app:Application): RoomService{
        return Room.databaseBuilder(
            app,
            RoomService::class.java,
            RoomService.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

}