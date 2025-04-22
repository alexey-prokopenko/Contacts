package com.example.contacts.ui.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contacts.R
import com.example.contacts.ui.theme.ExampleTheme

/**
 * Диалог добавления контакта
 *
 * @param state состояние диалога
 * @param onAddContactClick обработчик добавления контакта
 * @param modifier модификатор
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddContactDialog(
    state: MutableState<Boolean>,
    onAddContactClick: (lastName: String, firstName: String, middleName: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!state.value) return

    val dismissDialog = { state.value = false }
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }

    with(MaterialTheme) {
        BasicAlertDialog(
            onDismissRequest = dismissDialog,
            modifier = modifier,
        ) {
            Surface(
                shape = shapes.medium,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.title_add_contact),
                        modifier = Modifier.padding(bottom = 16.dp),
                        style = typography.titleLarge,
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        modifier = Modifier.padding(bottom = 8.dp),
                        textStyle = typography.bodyLarge,
                        label = { Text(text = stringResource(R.string.label_last_name)) }
                    )
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        modifier = Modifier.padding(bottom = 8.dp),
                        textStyle = typography.bodyLarge,
                        label = { Text(text = stringResource(R.string.label_first_name)) }
                    )
                    OutlinedTextField(
                        value = middleName,
                        onValueChange = { middleName = it },
                        modifier = Modifier.padding(bottom = 8.dp),
                        textStyle = typography.bodyLarge,
                        label = { Text(text = stringResource(R.string.label_middle_name)) }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        TextButton(onClick = dismissDialog) {
                            Text(text = stringResource(R.string.action_cancel))
                        }
                        TextButton(
                            onClick = {
                                onAddContactClick(lastName, firstName, middleName)
                                dismissDialog()
                            }
                        ) {
                            Text(text = stringResource(R.string.action_add_contact))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AddContactDialogPreview() {
    ExampleTheme {
        val dialogState = remember { mutableStateOf(true) }
        AddContactDialog(
            state = dialogState,
            onAddContactClick = { _, _, _ -> },
        )
    }
}
