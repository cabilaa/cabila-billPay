package com.cabila0046.assessment1.ui.screen

import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cabila0046.assessment1.R
import com.cabila0046.assessment1.navigation.Screen
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
                        navController.navigate(Screen.Bunga.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.about),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.about),
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
    val context = LocalContext.current

    var loan by rememberSaveable { mutableStateOf("") }
    var loanError by rememberSaveable { mutableStateOf(false) }

    var interest by rememberSaveable { mutableStateOf("") }
    var interestError by rememberSaveable { mutableStateOf(false) }

    val choose = listOf(
        stringResource(R.string.month_6),
        stringResource(R.string.month_12),
        stringResource(R.string.month_18),
        stringResource(R.string.month_24),
        stringResource(R.string.other)
    )
    var selectedChoose by rememberSaveable { mutableStateOf(choose[0]) }
    var custom by rememberSaveable { mutableStateOf("") }
    var customError by rememberSaveable { mutableStateOf(false) }
    val isOther = selectedChoose == stringResource(R.string.other)

    var expanded by rememberSaveable { mutableStateOf(false) }
    var resultText by rememberSaveable { mutableStateOf("") }

    var message by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.intro_app),
            style = MaterialTheme.typography.bodyLarge,
        )
        OutlinedTextField(
            value = loan,
            onValueChange = { loan = it },
            label = { Text(text = stringResource(R.string.total_loan)) },
            trailingIcon = { IconPicker(loanError, "Rp") },
            supportingText = { ErrorHint(loanError) },
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
            label = { Text(text = stringResource(R.string.interest)) },
            trailingIcon = { IconPicker(interestError, " % ") },
            supportingText = { ErrorHint(interestError) },
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
                label = { Text(stringResource(R.string.choose)) },
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
        if (isOther) {
            OutlinedTextField(
                value = custom,
                onValueChange = { custom = it },
                label = { Text(text = stringResource(R.string.loan_duration)) },
                trailingIcon = { IconPicker(customError, " month ") },
                supportingText = { if (customError) {
                                    Text(stringResource(R.string.input_invalid))
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

                val durationValue = if (isOther) custom else selectedChoose.filter { it.isDigit() }
                customError = isOther && (durationValue.isBlank() || durationValue == "0")

                if (loanError || interestError  ) return@Button

                val loanAmount = loan.toDoubleOrNull() ?: 0.0
                val interestRate = interest.toDoubleOrNull() ?: 0.0
                val durationMonths = durationValue.toIntOrNull() ?: 0

                if (loanAmount == 0.0 || interestRate == 0.0 || durationMonths == 0) return@Button

                val monthlyInterest = calculateMonthly(loanAmount, interestRate)
                val totalInterest = calculateTotal(monthlyInterest, durationMonths)
                val totalPayment = totalPayment(loanAmount, totalInterest)

                resultText = context.getString(
                    R.string.share_template,
                    "%,.0f".format(monthlyInterest),
                    "%,.0f".format(totalInterest),
                    "%,.0f".format(totalPayment),
                    durationMonths.toString()
                )

                message = context.getString(
                    R.string.share_template,
                    "%,.0f".format(monthlyInterest),
                    "%,.0f".format(totalInterest),
                    "%,.0f".format(totalPayment),
                    durationMonths.toString()
                )
                },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
        ) {
            Text(text = stringResource(R.string.count))
        }
        if (resultText.isNotBlank()) {
                Text(
                    text = resultText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp),

                    )
            }
        Button(
            onClick = {
                shareData(context, message)
            },
            modifier = Modifier.padding(top = 50.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(R.string.share))
        }
        Button(
            onClick = {
                loan = ""
                interest = ""
                selectedChoose = choose[0]
                custom = ""

                loanError = false
                interestError = false
                customError = false

                resultText = ""
                message = ""
            },
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(R.string.reset))
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

private fun shareData (context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null){
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Assessment1Theme {
        MainScreen(rememberNavController())
    }
}


