package com.cabila0046.assessment1.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cabila0046.assessment1.R
import com.cabila0046.assessment1.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(navController: NavHostController)  {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R. string.recycle))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),




            )
        }
    ) { innerPadding ->
        RecycleBinContent(Modifier.padding(innerPadding))
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun RecycleBinContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.deletedData.collectAsState()
    val filteredData = data.filter { it.dihapus }

    var showDialog by remember { mutableStateOf(false) }
    var id by remember { mutableLongStateOf(0L) }

    if (filteredData.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize().padding(64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(text = stringResource(id = R.string.empty_list))
        }
    }
    else {
        LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItem(pinjaman = it) {
                        id = it.id
                        showDialog = true
                    }
                    HorizontalDivider()
                }
            }
        if (showDialog)
        AlertDialog(
            text = { Text(text = stringResource(R.string.ask_recycle)) },
            confirmButton = {
                Row {
                    TextButton(onClick = { showDialog = false
                        viewModel.undoDelete(id)
                    }) {
                        Text(text = stringResource(R.string.undo_button))
                    }
                    TextButton(onClick =  {
                        showDialog = false
                        viewModel.deletePermanent(id)
                    }) {
                        Text(text = stringResource(R.string.delete_button))
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = {  showDialog = false   }) {
                    Text(text = stringResource(R.string.cancel_button))
                }
            },
            onDismissRequest = { showDialog = false }
        )

    }
}

