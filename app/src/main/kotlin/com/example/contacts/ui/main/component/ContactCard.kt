package com.example.contacts.ui.main.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contacts.domain.model.Contact
import com.example.contacts.ui.main.state.ContactState
import com.example.contacts.ui.theme.ExampleTheme

/**
 * Компонент отображения контакта
 *
 * @param state состояние
 * @param modifier модификатор
 */
@Composable
internal fun ContactCard(
    state: ContactState,
    modifier: Modifier = Modifier,
) {
    with(MaterialTheme) {
        Card(
            modifier = modifier,
            shape = shapes.medium,

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = state.lastName,
                    color = colorScheme.primary,
                    style = typography.bodyLarge,
                )
                Text(
                    text = state.name,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                    color = colorScheme.secondary,
                    style = typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ContactCardPreview() {
    val state = ContactState(
        model = Contact(),
        lastName = "Фамилия",
        name = "Имя Отчество",
    )
    ExampleTheme {
        ContactCard(
            state = state,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
