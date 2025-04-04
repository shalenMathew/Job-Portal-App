package com.example.skoolsaver.data.remote_source

import com.example.skoolsaver.domain.model.JobModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("jobs")
   suspend  fun getJobs(): Response<List<JobModel>>

    @PUT("jobs/{id}")
    suspend fun updateJobById(
        @Path("id") id: String,
        @Body updatedJob: JobModel
    ): Response<Unit>

    @POST("jobs")
    suspend fun createJob(@Body jobModel: JobModel): Response<Unit>

    @DELETE("jobs/{id}")
    suspend fun deleteJob(@Path("id") id: String): Response<Unit>
}