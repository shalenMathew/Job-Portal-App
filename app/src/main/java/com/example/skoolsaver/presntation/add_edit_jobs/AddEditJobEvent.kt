package com.example.skoolsaver.presntation.add_edit_jobs

import androidx.compose.ui.focus.FocusState

sealed class AddEditJobEvent {
    // event simply means every single action an user can make

    data class EnteredTitle(val title:String):AddEditJobEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditJobEvent()
    data class EnteredContent(val content:String):AddEditJobEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditJobEvent()
    data class EnteredLocation(val location:String):AddEditJobEvent()
    data class ChangeLocationFocus(val focusState: FocusState): AddEditJobEvent()
//    data class ChangeColor(val color:Int):AddEditNoteEvent()
    object SaveJob:AddEditJobEvent()

}


