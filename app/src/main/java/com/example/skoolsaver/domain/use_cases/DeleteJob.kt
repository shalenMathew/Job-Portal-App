package com.example.skoolsaver.domain.use_cases

import com.example.skoolsaver.domain.repository.JobsRepository
import com.example.skoolsaver.util.Resource

class DeleteJob(val jobsRepository: JobsRepository) {

    suspend fun deleteJob(id:String): Resource<String>{
       return jobsRepository.deleteJob(id = id)
    }
}