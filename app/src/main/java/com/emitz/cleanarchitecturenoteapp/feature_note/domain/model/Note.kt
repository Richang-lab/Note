package com.emitz.cleanarchitecturenoteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emitz.cleanarchitecturenoteapp.ui.theme.*

/*
* Contains Entity of single Note and Relevant properties of a note  */

@Entity
data class Note(
    val title:String,
    val content:String,
    val timeStamp:Long,
    val color:Int,
    @PrimaryKey val id:Int? = null
) {
    companion object{
        val noteColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}
class InvalidNoteException(message:String):Exception(message)