package com.example.sliit_news.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    var item: String?,
    var description: String?, // Add description field
    var date: String? // Add date field
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
