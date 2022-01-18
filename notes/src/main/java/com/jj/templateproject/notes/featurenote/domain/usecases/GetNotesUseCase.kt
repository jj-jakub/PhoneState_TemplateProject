package com.jj.templateproject.notes.featurenote.domain.usecases

import com.jj.templateproject.notes.featurenote.domain.model.Note
import com.jj.templateproject.notes.featurenote.domain.repository.NoteRepository
import com.jj.templateproject.notes.featurenote.domain.utils.NoteOrder
import com.jj.templateproject.notes.featurenote.domain.utils.NoteOrder.Color
import com.jj.templateproject.notes.featurenote.domain.utils.NoteOrder.Date
import com.jj.templateproject.notes.featurenote.domain.utils.NoteOrder.Title
import com.jj.templateproject.notes.featurenote.domain.utils.OrderType
import com.jj.templateproject.notes.featurenote.domain.utils.OrderType.Ascending
import com.jj.templateproject.notes.featurenote.domain.utils.OrderType.Descending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
        private val noteRepository: NoteRepository
) {

    operator fun invoke(noteOrder: NoteOrder = Date(Descending)): Flow<List<Note>> {
        return noteRepository.getNotes().map { notes ->
            when (noteOrder) {
                is Color -> sortByColor(noteOrder.orderType, notes)
                is Date -> sortByTimestamp(noteOrder.orderType, notes)
                is Title -> sortByTitle(noteOrder.orderType, notes)
            }
        }
    }

    private fun sortByColor(orderType: OrderType, notes: List<Note>): List<Note> =
        when (orderType) {
            Ascending -> notes.sortedBy { it.color }
            Descending -> notes.sortedByDescending { it.color }
        }

    private fun sortByTimestamp(orderType: OrderType, notes: List<Note>): List<Note> =
        when (orderType) {
            Ascending -> notes.sortedBy { it.timestamp }
            Descending -> notes.sortedByDescending { it.timestamp }
        }

    private fun sortByTitle(orderType: OrderType, notes: List<Note>): List<Note> =
        when (orderType) {
            Ascending -> notes.sortedBy { it.title.lowercase() }
            Descending -> notes.sortedByDescending { it.title.lowercase() }
        }
}