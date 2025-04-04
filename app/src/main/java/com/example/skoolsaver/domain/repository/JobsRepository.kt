package com.example.skoolsaver.domain.repository

import com.example.skoolsaver.domain.model.JobModel
import com.example.skoolsaver.util.Resource
import kotlinx.coroutines.flow.Flow


interface JobsRepository {

    fun getJobs(): Flow<Resource<List<JobModel>>>

    suspend fun updateJobById(id: String,updatedJob: JobModel): Resource<String>

    suspend fun insertJob(jobModel: JobModel) : Resource<String>

    suspend fun deleteJob(id: String) : Resource<String>
}
