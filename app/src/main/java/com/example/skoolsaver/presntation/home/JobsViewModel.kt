package com.example.skoolsaver.presntation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skoolsaver.domain.model.JobModel
import com.example.skoolsaver.domain.use_cases.JobsUseCases
import com.example.skoolsaver.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobsUseCases: JobsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(JobsState())
    val state: State<JobsState> = _state

    private var recentlyDeletedJob: JobModel? = null
    private var JobKtx: Job? = null

    init {
        Log.d("TAG","init")
        getJobs()
    }

    fun onEvent(jobEvent: JobEvent) {
        when (jobEvent) {
            is JobEvent.DeleteJob -> deleteJob(jobEvent.jobModel)
            is JobEvent.RestoreJob -> restoreJob()
        }
    }

   fun getJobs() {
        JobKtx?.cancel() // Cancel any previous job to avoid redundant API calls

        JobKtx = jobsUseCases.getJobs()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d("TAG","loading")
                        _state.value = _state.value.copy(loading = true)
                    }
                    is Resource.Success -> {

                        Log.d("TAG", result.data?.size.toString())

                        _state.value = _state.value.copy(
                            jobModels = result.data ?: emptyList(),
                            loading = false,
                            error = ""
                        )
                    }
                    is Resource.Error -> {

                        Log.d("TAG", result.message.toString())

                        _state.value = _state.value.copy(
                            error = result.message ?: "Unexpected error",
                            loading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun deleteJob(job: JobModel) {
        viewModelScope.launch {
            val result = jobsUseCases.deleteJob.deleteJob(job._id ?: return@launch)
            if (result is Resource.Success) {
                recentlyDeletedJob = job
                _state.value = _state.value.copy(statusMessage = result.data!!)
            } else if (result is Resource.Error) {
                _state.value = _state.value.copy(statusMessage = result.message!!)
            }

            getJobs()
        }
    }

    private fun restoreJob() {
        viewModelScope.launch {
            recentlyDeletedJob?.let { job ->
                jobsUseCases.addJob(job)
                recentlyDeletedJob = null
                _state.value = _state.value.copy(statusMessage = "Job restored successfully!")
            }
        }
        getJobs()
    }
}
