package com.example.reportsfordrivers.viewmodel.firstentry

import androidx.compose.runtime.snapshots.SnapshotStateList

object SortFun {

    fun sortSearchCountry(name: String, list: SnapshotStateList<CountryDetailing>): SnapshotStateList<CountryDetailing> {
        val a = SnapshotStateList<CountryDetailing>()
        list.forEach { if(name == it.country) a.add(it) }
        return a
    }
}