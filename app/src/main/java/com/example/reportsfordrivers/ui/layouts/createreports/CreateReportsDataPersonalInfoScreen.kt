package com.example.reportsfordrivers.ui.layouts.createreports

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.reportsfordrivers.R
import com.example.reportsfordrivers.Tags
import com.example.reportsfordrivers.navigate.ReportsForDriversSchema
import com.example.reportsfordrivers.ui.layouts.custom.BottomBarCustom
import com.example.reportsfordrivers.ui.layouts.custom.OutlinedTextFieldCustom
import com.example.reportsfordrivers.ui.layouts.custom.TabRowCustom
import com.example.reportsfordrivers.viewmodel.createreports.CreatePersonalInfoViewModel

@Composable
fun CreateReportsDataPersonalInfoScreen(
    viewModel: CreatePersonalInfoViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    BackHandler {
        navController.navigate(
            ReportsForDriversSchema.ReportInfo.name,
            navOptions = NavOptions.Builder()
                .setPopUpTo(ReportsForDriversSchema.ReportInfo.name, true)
                .build()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRowCustom(
            index = 1,
            navController = navController,
            isEnabledTwo = false,
            isEnabledThree = viewModel.uiStateIsValidate.value.isValidateCreatePersonalInfo,
            isEnabledFour = viewModel.uiStateIsValidate.value.isValidateCreateVehicleTrailer,
            isEnabledFive = viewModel.uiStateIsValidate.value.isValidateCreateRoute,
            isEnabledSix = viewModel.uiStateIsValidate.value.isValidateCreateProgressReports
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 16.dp, start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            OutlinedTextFieldCustom(
                label = R.string.last_name,
                value = viewModel.uiStateCreatePersonalInfo.value.lastName,
                onValueChange = viewModel::updateDataCreatePersonalInfoLastName,
                tag = Tags.TAG_TEST_DATA_PERSONAL_INFO_LAST_NAME,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            OutlinedTextFieldCustom(
                label = R.string.first_name,
                value = viewModel.uiStateCreatePersonalInfo.value.firstName,
                onValueChange = viewModel::updateDataCreatePersonalInfoFirstName,
                tag = Tags.TAG_TEST_DATA_PERSONAL_INFO_FIRST_NAME,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )

            OutlinedTextFieldCustom(
                label = R.string.patronymic,
                value = viewModel.uiStateCreatePersonalInfo.value.patronymic,
                onValueChange = viewModel::updateDataCreatePersonalInfoPatronymic,
                tag = Tags.TAG_TEST_DATA_PERSONAL_INFO_PATRONYMIC,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )
        }

        BottomBarCustom(
            onNext = { navController.navigate(ReportsForDriversSchema.VehicleInfo.name) },
            onBack = { navController.navigateUp() },
            enabled = viewModel.isValidateDataCreatePersonalInfo()
        )
    }
}