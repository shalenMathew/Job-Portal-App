package com.example.skoolsaver.data.repository

import android.util.Log
import com.example.skoolsaver.data.remote_source.ApiService
import com.example.skoolsaver.domain.model.JobModel
import com.example.skoolsaver.domain.repository.JobsRepository
import com.example.skoolsaver.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class JobsRepositoryImpl @Inject constructor(val apiService: ApiService): JobsRepository {

    override fun getJobs(): Flow<Resource<List<JobModel>>> {

        return  flow {

            try {
                emit(Resource.Loading())

                val response = apiService.getJobs()

                if (response.isSuccessful){
                    val jobs = response.body()

                    emit(Resource.Success(data = jobs ?: emptyList()))
                }else{
                    emit(Resource.Error(message = response.message()))
                }

            }catch (e: Exception){
                emit(Resource.Error(message = e.message.toString()))
            }

        }.flowOn(Dispatchers.IO)

    }

    override suspend fun updateJobById(id: String, updatedJob: JobModel): Resource<String> {
        return try {
            val jobResponse = apiService.updateJobById(id, updatedJob)

            if (jobResponse.isSuccessful) {
                Resource.Success("Job posting updated successfully!")
            } else {
                Resource.Error(message = jobResponse.message())
            }
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown error occurred")
        }
    }



    override suspend fun insertJob(jobModel: JobModel): Resource<String> {
        return try {
            val response = apiService.createJob(jobModel)

            if (response.isSuccessful) {
                Resource.Success("Job posting created successfully!")
            } else {
                Resource.Error("Failed to create job: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun deleteJob(id: String): Resource<String> {
        return try {
            val response = apiService.deleteJob(id)

            if (response.isSuccessful) {
                Resource.Success("Job posting deleted successfully!")
            } else {
                Resource.Error("Failed to delete job: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }



}