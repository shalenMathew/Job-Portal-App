package com.example.skoolsaver.presntation.add_edit_jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skoolsaver.presntation.add_edit_jobs.components.TransparentHintTextField
import com.example.skoolsaver.presntation.home.JobsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


//designing the screen for update section

@Composable
fun AddEditNoteScreen(
navController: NavController,
viewModel: AddEditJobViewModel = hiltViewModel(),
jobViewModel: JobsViewModel = hiltViewModel()
){

    val titleState = viewModel.jobTitle.value
    val contentState = viewModel.jobDes.value
    val locState = viewModel.jobLoc.value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

//    val noteBackgroundAnimatable = remember {
//        Animatable(
//            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
//            // -1 will occur when we click the add note btn if add note btn is clicked then the logic suggest the screen to take some random color,
//            // the if  its not -1 it mean its some no which indicates its a noteId ...then take the noteColor of the note as the screen color instead of any
//        // random color
//        )
//    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true ){

        viewModel.eventFlow.collectLatest { event->

            when(event){

                is AddEditJobViewModel.UiEvent.ShowSnackbar->{
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }

                is AddEditJobViewModel.UiEvent.SaveNote->{
                    jobViewModel.getJobs()
                    navController.navigateUp()
                }

            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {viewModel.onEvent(AddEditJobEvent.SaveJob)},
                containerColor = MaterialTheme.colorScheme.onPrimary)
            {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
        }
        },
        snackbarHost ={SnackbarHost(snackbarHostState)}
    ) { it->

        Column(modifier= Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(it),
            ) {



            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = { enteredTitle ->
                    // remember can't directly change the textView state
                    viewModel.onEvent(AddEditJobEvent.EnteredTitle(enteredTitle))
                } ,
                onFocusChange = {
                    viewModel.onEvent(AddEditJobEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible , // we r taking care of the logic of isHintVisible in ChangeTitleFocus(it), we have to just fetch the isHintVisible
                textStyle = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {enteredContent->
                    // remember can't directly change the textView state
                    viewModel.onEvent(AddEditJobEvent.EnteredContent(enteredContent))
                } ,
                onFocusChange = {
                    viewModel.onEvent(AddEditJobEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible , // we r taking care of the logic of isHintVisible in ChangeTitleFocus(it),
                // we have to just fetch the isHintVisible
                textStyle = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(15.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransparentHintTextField(
                text = locState.text,
                hint = locState.hint,
                onValueChange = { enteredLocation ->
                    // remember can't directly change the textView state
                    viewModel.onEvent(AddEditJobEvent.EnteredLocation(enteredLocation))
                } ,
                onFocusChange = {
                    viewModel.onEvent(AddEditJobEvent.ChangeLocationFocus(it))
                },
                isHintVisible = locState.isHintVisible , // we r taking care of the logic of isHintVisible in ChangeTitleFocus(it), we have to just fetch the isHintVisible
                textStyle = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 12.dp)
            )

        }


    }

}