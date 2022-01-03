package com.emitz.cleanarchitecturenoteapp.feature_note.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emitz.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.emitz.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCases
import com.emitz.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.emitz.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel(){

    private val _state =  mutableStateOf(NoteState())
    val state : State<NoteState> = _state

    private var recentlyDeletedNote : Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Ascending))
    }

    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.DeleteNote->{
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NoteEvent.Order->{
                if(state.value.notesOrder::class == event.noteOrder::class &&
                        state.value.notesOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNotes(event.noteOrder)
            }
            is NoteEvent.RestoreNote->{
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvent.ToggleOrderSection->{
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
    private fun getNotes(noteOrder:NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach{notes->
                _state.value = state.value.copy(
                    notes = notes,
                    notesOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}