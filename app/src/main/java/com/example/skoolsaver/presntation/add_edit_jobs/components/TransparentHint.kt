package com.example.skoolsaver.presntation.add_edit_jobs.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color

@Composable
fun TransparentHintTextField(
    text:String,
    hint:String,
    modifier:Modifier=Modifier,
    isHintVisible:Boolean =true,
    onValueChange:(String)->Unit,
    textStyle: androidx.compose.ui.text.TextStyle = androidx.compose.ui.text.TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
){

Box(modifier=modifier){
    
    BasicTextField(value = text,
        onValueChange = onValueChange, // on value change means when someones enter a text
        singleLine=singleLine,
        textStyle=textStyle,
        modifier=Modifier.
        fillMaxWidth()
            .onFocusChanged{
                onFocusChange(it)
            }
        )

    if (isHintVisible){
        Text(text=hint, style=textStyle, color = Color.DarkGray)
    }
    
}

}