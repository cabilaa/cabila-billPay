package com.cabila0046.assessment1.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cabila0046.assessment1.R
import com.cabila0046.assessment1.Screen
import com.cabila0046.assessment1.ui.theme.Assessment1Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController)  {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R. string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var loan by remember { mutableStateOf("") }
    var loanError by remember { mutableStateOf(false) }

    var interest by remember { mutableStateOf("") }
    var interestError by remember { mutableStateOf(false) }

    val choose = stringArrayResource(id = R.array.pilih_item)
    var selectedChoose by remember { mutableStateOf(choose[0]) }


    var custom by remember { mutableStateOf("") }
    var customError by remember { mutableStateOf(false) }
    val isOther = selectedChoose == "other"
    val durationValue = if (isOther) custom else selectedChoose.filter { it.isDigit() }
    customError = (durationValue.isBlank() || durationValue == "0")


    var expanded by remember { mutableStateOf(false) }

    var resultText by remember { mutableStateOf("") }



    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
        )
        OutlinedTextField(
            value = loan,
            onValueChange = { loan = it },
            label = { Text(text = stringResource(R.string.total_hutang)) },
            trailingIcon = { IconPicker(loanError, "Rp") },
            supportingText = { ErrorHint(loanError)},
            isError = loanError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = interest,
            onValueChange = { interest = it },
            label = { Text(text = stringResource(R.string.bunga)) },
            trailingIcon = { IconPicker(interestError, " % ") },
            supportingText = { ErrorHint(interestError)},
            isError = interestError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedChoose,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.pilih)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .clickable { expanded = true }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                choose.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedChoose = option
                            expanded = false
                        }
                    )
                }
            }
        }
        if (selectedChoose == "other") {
            OutlinedTextField(
                value = custom,
                onValueChange = { custom = it },
                label = { Text("Enter Duration") },
                trailingIcon = { IconPicker(customError, " month ") },
                supportingText = { if(customError && selectedChoose == "other") {
                                    Text(stringResource(R.string.error))
                                    }
                                 },
                isError = customError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            onClick = {
                loanError = (loan == "" || loan == "0")
                interestError = (interest == "" || interest == "0")
                if (loanError || interestError || customError ) return@Button

                val loanAmount = loan.toDoubleOrNull() ?: 0.0
                val interestRate = interest.toDoubleOrNull() ?: 0.0

                val durationMonths = when {
                    selectedChoose == "other" -> custom.toIntOrNull() ?: 0
                    else -> selectedChoose.filter { it.isDigit() }.toIntOrNull() ?: 0
                }

                if (loanAmount == 0.0 || interestRate == 0.0 || durationMonths == 0) return@Button

                val monthlyInterest = calculateMonthly(loanAmount, interestRate)
                val totalInterest = calculateTotal(monthlyInterest, durationMonths)
                val totalPayment = totalPayment(loanAmount, totalInterest)

                resultText = """
                Monthly Interest       : Rp ${"%,.0f".format(monthlyInterest)}
                Total Interest         : Rp ${"%,.0f".format(totalInterest)}
                Total Payment          : Rp ${"%,.0f".format(totalPayment)}
                Payment Duration       : $durationMonths months
                """.trimIndent()
                },


            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }
        if (resultText.isNotEmpty()) {
            Text(text = resultText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String){
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint (isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

fun calculateMonthly(amount: Double, ratePercent: Double): Double {
    return amount * (ratePercent / 100)
}

fun calculateTotal(monthlyInterest: Double, months: Int): Double {
    return monthlyInterest * months
}

fun totalPayment(amount: Double, totalInterest: Double): Double {
    return amount + totalInterest
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Assessment1Theme {
        MainScreen(rememberNavController())
    }
}


