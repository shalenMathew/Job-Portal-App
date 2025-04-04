package com.example.skoolsaver.di


import com.example.skoolsaver.data.remote_source.ApiService
import com.example.skoolsaver.domain.repository.JobsRepository
import com.example.skoolsaver.domain.use_cases.AddJob
import com.example.skoolsaver.domain.use_cases.DeleteJob
import com.example.skoolsaver.domain.use_cases.UpdateJob
import com.example.skoolsaver.domain.use_cases.GetJobs
import com.example.skoolsaver.domain.use_cases.JobsUseCases
import com.example.skoolsaver.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun providesNotesRepository(db:NotesDatabase):NotesRepository{
//        return NotesRepositoryImpl(db.notesDao)
//    }


    @Provides
    @Singleton
    fun getApiService(): ApiService{

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesNotesUseCases(jobsRepository: JobsRepository): JobsUseCases{
        return JobsUseCases(getJobs = GetJobs(jobsRepository), addJob = AddJob(jobsRepository) , deleteJob = DeleteJob(jobsRepository),
           updateJob =  UpdateJob(jobsRepository)
        )
    }

}