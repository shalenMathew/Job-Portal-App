package com.example.skoolsaver.presntation.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skoolsaver.presntation.home.components.JobItem
import com.example.skoolsaver.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobScreen(
    navController: NavController,
    viewModel: JobsViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val pullToRefresh = rememberPullToRefreshState()
    var isRefreshing by remember {
        mutableStateOf(false)
    }

// Jetpack Compose Scaffold is a pre-designed layout component in the Jetpack Compose UI toolkit.

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditJobScreen.route) }, containerColor = MaterialTheme.colorScheme.onPrimary )
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        snackbarHost = {
            SnackbarHost(scaffoldState)
        }
    ) {it->

//        if (state.jobModels.isNotEmpty()){

            PullToRefreshBox(
                isRefreshing = isRefreshing ,
                onRefresh ={
                    scope.launch {
                        isRefreshing =true
                        viewModel.getJobs()
                        delay(1000)
                        isRefreshing=false
                    }
                } ,
                state = pullToRefresh
            ) {

                Column(modifier = Modifier
                    .padding(it)
                    .fillMaxSize())
                {


                    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,) {

                        Text(text = "Jobs",modifier=Modifier.padding(horizontal = 12.dp,
                            vertical = 12.dp),color = Color.White,
                            style = MaterialTheme.typography.labelLarge, fontSize =30.sp,
                        )

                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // notes list starts from here

                    LazyColumn(modifier= Modifier.fillMaxSize()){
                        items(state.jobModels){ job->

                            Log.d("TAG",job.title.toString())

                            JobItem(jobModel = job,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(
                                        Screen.AddEditJobScreen.route +
                                                "?noteId=${job._id}"
                                    )},
                                onDeleteClick = {
                                    viewModel.onEvent(JobEvent.DeleteJob(job))

                                    // we should launch a snack-bar in coroutine
                                    scope.launch {
                                        val result = scaffoldState.showSnackbar(
                                            message ="Job posting deleted",
                                            actionLabel = "Undo",
                                            duration = androidx.compose.material3.SnackbarDuration.Short
                                        )

                                        if (result==SnackbarResult.ActionPerformed){
                                            viewModel.onEvent(JobEvent.RestoreJob)
                                        }
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                    }
                }

            }
//        }

        if (state.error.isNotEmpty()){
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                if(state.error == "timeout"){
                    Text(text = "pull to refresh", fontSize = 20.sp, color = Color.White)
                }else{
                    Text(text = state.error, fontSize = 20.sp, color = Color.White)
                }


            }
        }

        if (state.loading){
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }


    }

}