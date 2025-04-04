package com.example.skoolsaver.domain.use_cases

import com.example.skoolsaver.domain.model.JobModel
import com.example.skoolsaver.domain.repository.JobsRepository
import com.example.skoolsaver.util.Resource

class UpdateJob(val repository: JobsRepository) {

    suspend operator fun invoke(id: String,updatedJob: JobModel): Resource<String>{
    return repository.updateJobById(id,updatedJob)
}
}