package com.example.TakeYourMedicine.model.graph

import com.example.TakeYourMedicine.view.adapters.ItemList
import java.util.Date

data class Graph(
    val data: MutableMap<Date, Int>
) : ItemList