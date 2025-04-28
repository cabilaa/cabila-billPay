package com.cabila0046.assessment1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cabila0046.assessment1.R
import com.cabila0046.assessment1.ui.theme.Assessment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BungaDetailScreen(){
    var nama by remember { mutableStateOf("") }
    var lama by remember { mutableStateOf("") }
    var bunga by remember { mutableStateOf("") }
    var selectedChoose by remember { mutableStateOf("") }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.add_loan))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        FormPinjaman(
            title = nama,
            onTitleChange = { nama = it },
            lama = lama,
            onLamaChange = { lama = it},
            bunga = bunga,
            onBungaChange = { bunga = it },
            selectedChoose = selectedChoose,
            selectedChooseChange = { selectedChoose = it },
            modifier = Modifier.padding(padding)


        )


    }
}

@Composable
fun FormPinjaman(
    title: String, onTitleChange: (String) -> Unit,
    lama: String, onLamaChange: (String) -> Unit,
    bunga: String, onBungaChange: (String) -> Unit,
    selectedChoose: String, selectedChooseChange: (String) -> Unit,
    modifier: Modifier


) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(R.string.name)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lama,
            onValueChange = { onLamaChange(it) },
            label = { Text(text = stringResource(R.string.total_loan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = bunga,
            onValueChange = { onBungaChange(it) },
            label = { Text(text = stringResource(R.string.loan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()

        )
        OutlinedTextField(
        value = selectedChoose,
        onValueChange = { selectedChooseChange(it) },
        label = { Text(text = stringResource(R.string.loan)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BungaDetailScreenPreview() {
    Assessment1Theme {
        BungaDetailScreen()
    }
}