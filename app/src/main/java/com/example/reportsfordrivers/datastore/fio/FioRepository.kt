package com.example.reportsfordrivers.datastore.fio

interface FioRepository {

    suspend fun setFirstName(firstName: String)

    suspend fun getFirstName(): Result<String>

    suspend fun setLastName(lastName: String)

    suspend fun getLastName(): Result<String>

    suspend fun setPatronymic(patronymic: String)

    suspend fun getPatronymic(): Result<String>
}