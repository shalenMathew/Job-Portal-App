package com.example.skoolsaver.presntation.home

import com.example.skoolsaver.domain.model.JobModel


data class JobsState(
    val jobModels:List<JobModel> = emptyList(),
    val loading: Boolean = false,
    val statusMessage:String = "",
    val error:String=""
)
