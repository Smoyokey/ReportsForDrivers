package com.example.reportsfordrivers.viewmodel.firstentry

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.Flow

object SortFun {

    fun sortSearchCountry(name: String, list: SnapshotStateList<CountryDetailing>): SnapshotStateList<CountryDetailing> {
        val a = SnapshotStateList<CountryDetailing>()
        list.forEach { if(name == it.country) a.add(it) }
        return a
    }
}

//fun <T1, T2, R> combine (
//    flow: Flow<T1>,
//    flow2: Flow<T2>,
//    transform: suspend (T1, T2) -> R
//): Flow<R> = combine(
//
//)