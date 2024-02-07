package com.example.reportsfordrivers.datastore.firstentry

interface FirstEntryRepository {

    suspend fun setFirstEntry(isFirstEntry: Boolean)

    suspend fun getFirstEntry(): Result<Boolean>
}