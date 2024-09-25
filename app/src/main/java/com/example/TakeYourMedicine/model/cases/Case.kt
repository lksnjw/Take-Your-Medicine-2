package com.example.TakeYourMedicine.model.cases

import com.example.TakeYourMedicine.view.adapters.ItemList


data class Case (
    val id: Long,
    var comment: String,
    var date: Long,
    val habitId: Long
) : ItemList