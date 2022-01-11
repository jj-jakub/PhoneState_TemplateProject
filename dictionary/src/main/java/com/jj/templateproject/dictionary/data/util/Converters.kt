package com.jj.templateproject.dictionary.data.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.jj.templateproject.dictionary.domain.model.Meaning

@ProvidedTypeConverter
class Converters(
        private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
                json, object : TypeToken<ArrayList<Meaning>>() {}.type)
            ?: emptyList()
    }

    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
                meanings, object : TypeToken<ArrayList<Meaning>>() {}.type
        ) ?: "[]"
    }
}