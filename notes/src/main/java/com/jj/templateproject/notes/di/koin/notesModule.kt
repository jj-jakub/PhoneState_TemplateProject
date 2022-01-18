package com.jj.templateproject.notes.di.koin

import androidx.room.Room
import com.jj.templateproject.notes.featurenote.data.NoteRepositoryImpl
import com.jj.templateproject.notes.featurenote.data.local.NoteDatabase
import com.jj.templateproject.notes.featurenote.domain.repository.NoteRepository
import com.jj.templateproject.notes.featurenote.domain.usecases.AddNoteUseCase
import com.jj.templateproject.notes.featurenote.domain.usecases.DeleteNoteUseCase
import com.jj.templateproject.notes.featurenote.domain.usecases.GetNotesUseCase
import com.jj.templateproject.notes.featurenote.domain.usecases.NoteUseCases
import com.jj.templateproject.notes.featurenote.presentation.viewmodels.NotesMainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {

    single { Room.databaseBuilder(androidContext(), NoteDatabase::class.java, NoteDatabase.NOTE_ROOM_DB_NAME).build() }

    single<NoteRepository> { NoteRepositoryImpl(get<NoteDatabase>().noteDao) }

    single { NoteUseCases(GetNotesUseCase(get()), AddNoteUseCase(get()), DeleteNoteUseCase(get())) }

    viewModel { NotesMainViewModel(get()) }
}