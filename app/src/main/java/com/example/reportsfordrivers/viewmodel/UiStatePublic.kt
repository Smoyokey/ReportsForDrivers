package com.example.reportsfordrivers.viewmodel

/**
 * Класс для сохранения транспортного средства или прицепа с полями Марка, Гос номер, и тип ТС
 * (авто или прицеп)
 */

data class ObjectVehicle(
    val type: String = "",
    val make: String = "",
    val rn: String = "",
)