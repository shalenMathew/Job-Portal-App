package com.example.skoolsaver.domain.model

// declaring model in domain layer,as different business will have different models which means model
// class comes under business logic which is usually declared in domain class

data class JobModel(
    val _id: String?,
    val title: String,
    val description: String,
    val location: String
)

class InvalidNoteException(message: String): Exception(message)


