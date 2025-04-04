package com.example.skoolsaver.domain.use_cases

import com.example.skoolsaver.domain.model.JobModel
import com.example.skoolsaver.domain.repository.JobsRepository
import com.example.skoolsaver.util.Resource
import kotlinx.coroutines.flow.Flow

class GetJobs(private val jobsRepository: JobsRepository) {
    operator fun invoke(): Flow<Resource<List<JobModel>>> {
        return jobsRepository.getJobs()
    }
}