package com.example.skoolsaver.presntation.home

import com.example.skoolsaver.domain.model.JobModel


sealed class JobEvent {
    data class DeleteJob(val jobModel: JobModel) : JobEvent()
    data object RestoreJob:JobEvent()
}