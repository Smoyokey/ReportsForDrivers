package com.example.reportsfordrivers.datastore

interface FirstEntryRepository {

    suspend fun setFirstEntry(isFirstEntry: Boolean)

    suspend fun getFirstEntry(): Result<Boolean>
}