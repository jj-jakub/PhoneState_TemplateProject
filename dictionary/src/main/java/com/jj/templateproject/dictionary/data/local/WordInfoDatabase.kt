package com.jj.templateproject.dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jj.templateproject.dictionary.data.local.entity.WordInfoEntity
import com.jj.templateproject.dictionary.data.util.Converters

@Database(
        entities = [WordInfoEntity::class],
        version = 1
)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase : RoomDatabase() {

    abstract val dao: WordInfoDao
}