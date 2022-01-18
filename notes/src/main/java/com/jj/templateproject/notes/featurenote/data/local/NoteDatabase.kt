package com.jj.templateproject.notes.featurenote.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jj.templateproject.notes.featurenote.domain.model.Note

@Database(
        entities = [Note::class],
        version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
}