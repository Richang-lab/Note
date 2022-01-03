package com.emitz.cleanarchitecturenoteapp.feature_note.presentation.note

import com.emitz.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.emitz.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.emitz.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

data class NoteState(
    val notes : List<Note> = emptyList(),
    val notesOrder : NoteOrder = NoteOrder.Date(OrderType.Ascending),
    val isOrderSectionVisible : Boolean = false
)
