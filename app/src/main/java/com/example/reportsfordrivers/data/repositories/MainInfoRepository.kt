package com.example.reportsfordrivers.data.repositories

import com.example.reportsfordrivers.data.structure.MainInfo
import kotlinx.coroutines.flow.Flow

interface MainInfoRepository {
    fun getAllItemStream() : Flow<List<MainInfo>>

    fun getOneItemStream(id: Int) : Flow<MainInfo?>

    suspend fun insertItem (item: MainInfo)

    suspend fun deleteItem (item: MainInfo)

    suspend fun updateItem (item: MainInfo)
}