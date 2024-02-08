package com.example.reportsfordrivers.viewmodel.firstentry


data class FirstEntryUiState(
    var itemDetails: FioItemDetails = FioItemDetails(),
    var isSelected: IsSelectedVehicleAndTrailer = IsSelectedVehicleAndTrailer(),
    var makeRnItemDetails: MakeRnItemDetails = MakeRnItemDetails(),
)

data class FioItemDetails (
    var lastName: String = "",
    val firstName: String = "",
    val patronymic: String = ""
)

data class IsSelectedVehicleAndTrailer(
    val stateRadioGroup: Boolean = true
)

data class MakeRnItemDetails (
    val make: String = "",
    val rn: String = ""
)

data class UiState(
    val lastName: String = "",
    val firstName: String = ""
)