package com.sujata.virginmoneydemo.framework.api.di

import com.sujata.virginmoneydemo.data.PeopleDataSource
import com.sujata.virginmoneydemo.data.RoomsDataSource
import com.sujata.virginmoneydemo.framework.PeoplesAPIDataSource
import com.sujata.virginmoneydemo.framework.RoomsAPIDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun providePeopleDataSource(peoplesAPIDataSource: PeoplesAPIDataSource): PeopleDataSource

    @Binds
    abstract fun provideRoomDataSource(roomsAPIDataSource: RoomsAPIDataSource): RoomsDataSource
}