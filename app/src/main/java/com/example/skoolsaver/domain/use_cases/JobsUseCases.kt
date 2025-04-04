package com.example.skoolsaver.domain.use_cases

data class JobsUseCases(
    val getJobs: GetJobs,
    val addJob: AddJob,
    val deleteJob: DeleteJob,
    val updateJob: UpdateJob
)

