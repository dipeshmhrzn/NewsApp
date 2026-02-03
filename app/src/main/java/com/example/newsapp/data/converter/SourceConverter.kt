package com.example.newsapp.data.converter

import androidx.room.TypeConverter
import com.example.newsapp.data.dto.topheadlines.Source
import kotlinx.serialization.json.Json

class SourceConverter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return Json.encodeToString(source)
    }

    @TypeConverter
    fun toSource(sourceString: String): Source {
        return Json.decodeFromString(sourceString)
    }
}