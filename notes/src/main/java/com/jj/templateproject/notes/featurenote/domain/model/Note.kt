package com.jj.templateproject.notes.featurenote.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jj.templateproject.notes.R

@Entity
data class Note(val title: String, val content: String, val timestamp: Long, val color: Int, @PrimaryKey val id: Int? = null) {

    companion object {
        val noteColorsResources = listOf(R.color.magenta, R.color.cyan, R.color.red, R.color.gray, R.color.green, R.color.yellow)
    }
}

