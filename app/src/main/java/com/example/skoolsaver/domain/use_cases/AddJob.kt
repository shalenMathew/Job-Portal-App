package com.example.skoolsaver.domain.use_cases

import com.example.skoolsaver.domain.model.InvalidNoteException
import com.example.skoolsaver.domain.model.JobModel
import com.example.skoolsaver.domain.repository.JobsRepository
import com.example.skoolsaver.util.Resource

class AddJob(val jobsRepository: JobsRepository) {


@Throws(InvalidNoteException::class)
    suspend operator fun invoke(jobModel: JobModel): Resource<String>{

        if(jobModel.title.isBlank()){
            throw InvalidNoteException("The title is empty")
        }

    if (jobModel.description.isBlank()){
        throw InvalidNoteException("The description is empty")
    }

     return jobsRepository.insertJob(jobModel)

    }

}