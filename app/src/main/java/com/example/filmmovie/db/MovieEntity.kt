package com.example.filmmovie.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmmovie.utils.Constants

@Entity(tableName = Constants.MOVIES_TABLE)
data class MovieEntity(
    @PrimaryKey
    var id : Int = 0,
    var poster: String = "",
    var title: String = "",
    var rate: String = "",
    var country: String = "",
    var year: String = ""
)
