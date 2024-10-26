package com.juani48.todoapp.application.di

import android.content.Context
import androidx.room.Room
import com.juani48.todoapp.repository.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDataBase::class.java, "APP_DATABASE").build()

    @Singleton
    @Provides
    fun provideTaskDao(db: AppDataBase) = db.getTaskDao()
}
