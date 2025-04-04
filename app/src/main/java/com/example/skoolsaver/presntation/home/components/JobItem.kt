package com.example.skoolsaver.presntation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.skoolsaver.domain.model.JobModel


// note design
@Composable
fun JobItem(
    jobModel:JobModel,
    modifier:Modifier=Modifier,
    onDeleteClick: () -> Unit
){
    Box(modifier=modifier){

        Column(modifier = Modifier
            .padding(horizontal = 12.dp)
            .background(color = Color.White)
            .padding(6.dp)
            .fillMaxSize()

        ) {

            Text(
                text = "Role : " + jobModel.title,
                modifier=Modifier.padding(start=5.dp),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
                )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Description : " + jobModel.description,
                modifier=Modifier.padding(5.dp).padding(bottom=5.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Location: " + jobModel.location,
                modifier=Modifier.padding(5.dp).padding(bottom=5.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                maxLines = 1
            )

        }
        
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd))
        {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note",
                tint = Color.Black
            )
        }
    }
}