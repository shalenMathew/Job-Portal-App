package com.example.skoolsaver.presntation.add_edit_jobs

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skoolsaver.domain.model.InvalidNoteException
import com.example.skoolsaver.domain.model.JobModel
import com.example.skoolsaver.domain.use_cases.JobsUseCases
import com.example.skoolsaver.presntation.home.JobsState
import com.example.skoolsaver.presntation.home.JobsViewModel
import com.example.skoolsaver.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditJobViewModel
@Inject constructor(val jobsUseCases: JobsUseCases,
                    savedStateHandle: SavedStateHandle,
    ):ViewModel() {


    private val _jobTitle = mutableStateOf(JobTextFieldState(hint = "Enter Job.."))
    val jobTitle: State<JobTextFieldState> = _jobTitle

    private val _jobDes = mutableStateOf(JobTextFieldState(hint = "Enter description.."))
    val jobDes: State<JobTextFieldState> = _jobDes

    private val _jobLoc = mutableStateOf(JobTextFieldState(hint = "Enter location.."))
    val jobLoc: State<JobTextFieldState> = _jobLoc


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var currJobsId: String? = null

    init {
        viewModelScope.launch {
            val jobId = savedStateHandle.get<String>("noteId") ?: return@launch

            if (jobId != "-1") {
                jobsUseCases.getJobs()
                    .collect { jobRes ->

                        when (jobRes) {
                            is Resource.Success -> {
                                val matchedJob = jobRes.data?.find { it._id == jobId }

                                if (matchedJob != null) {
                                    currJobsId = matchedJob._id

                                    _jobTitle.value = jobTitle.value.copy(
                                        text = matchedJob.title,
                                        isHintVisible = false
                                    )

                                    _jobDes.value = jobDes.value.copy(
                                        text = matchedJob.description,
                                        isHintVisible = false
                                    )

                                    _jobLoc.value = jobLoc.value.copy(
                                        text = matchedJob.location,
                                        isHintVisible = false
                                    )


                                } else {
                                    Log.d("TAG", "No job found with id: $jobId")
                                }
                            }

                            is Resource.Error -> {
                                Log.e("TAG", "Error fetching jobs: ${jobRes.message}")
                            }

                            is Resource.Loading -> {
                                Log.d("TAG", "Loading jobs...")
                            }
                        }
                    }
            }
        }
    }


    fun onEvent(event: AddEditJobEvent) {  // u can pass and object of 'AddEditNoteEvent' as parameter
        when (event) {

            is AddEditJobEvent.EnteredTitle -> {
                _jobTitle.value = jobTitle.value.copy(
                    text = event.title
                )
            }

            is AddEditJobEvent.ChangeTitleFocus -> {
                _jobTitle.value = jobTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && _jobTitle.value.text.isBlank()
                )
            }

            is AddEditJobEvent.EnteredLocation -> {
                _jobLoc.value = jobLoc.value.copy(
                    text = event.location
                )
            }

            is AddEditJobEvent.ChangeLocationFocus -> {
                _jobLoc.value = jobLoc.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && _jobLoc.value.text.isBlank()
                )
            }

            is AddEditJobEvent.EnteredContent -> {
                _jobDes.value = jobDes.value.copy(
                    text = event.content
                )
            }

            is AddEditJobEvent.ChangeContentFocus -> {
                _jobDes.value = jobDes.value.copy(
                    isHintVisible = !event.focusState.isFocused // we want to show hint when the focus is not on the content
                            && _jobDes.value.text.isBlank()
                )
            }

            is AddEditJobEvent.SaveJob -> {
                viewModelScope.launch {
                    try {
                        val job = JobModel(
                            title = jobTitle.value.text,
                            description = jobDes.value.text,
                            location = jobLoc.value.text,
                            _id = currJobsId // could be null for new jobs
                        )

                        if (currJobsId != null) {
                            // Update existing job
                            jobsUseCases.updateJob(job._id!!,job)
                        } else {
                            // Add new job
                            jobsUseCases.addJob(job)
                        }

                        _eventFlow.emit(UiEvent.SaveNote)

                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save job"
                            )
                        )
                    }
                }
            }



        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }

}