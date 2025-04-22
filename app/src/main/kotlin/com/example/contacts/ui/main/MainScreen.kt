package com.example.contacts.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contacts.R
import com.example.contacts.domain.model.Contact
import com.example.contacts.ui.main.MainViewModel.UiEffect
import com.example.contacts.ui.main.MainViewModel.UiEvent
import com.example.contacts.ui.main.component.AddContactDialog
import com.example.contacts.ui.main.component.ContactCard
import com.example.contacts.ui.main.state.ContactState
import com.example.contacts.ui.theme.ExampleTheme
import kotlinx.coroutines.launch

/**
 * Главный экран
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val scope = rememberCoroutineScope()

    val effectState = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    val addDialogState = remember { mutableStateOf(value = false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val isRefreshing = remember { mutableStateOf(value = false) }
    effectState.value?.let { effect ->
        LaunchedEffect(effect) {
            when (effect) {
                is UiEffect.AddNewContact -> addDialogState.value = true
                is UiEffect.ShowSnack -> snackbarHostState.showSnackbar(effect.message)
                is UiEffect.Refreshing -> isRefreshing.value = effect.isRefreshing
            }
        }
    }

    val state = viewModel.state
    PullToRefreshBox (
        modifier = Modifier
            .fillMaxSize(),
        isRefreshing = isRefreshing.value,
        onRefresh = {
            scope.launch {
                viewModel.event(UiEvent.UpdateContacts)
            }
        }
    ) {
        MainContent(
            snackbarHostState = snackbarHostState,
            contactStates = state.contactStates,
            onAddNewContactClick = { viewModel.event(UiEvent.AddNewContact) },
        )

        AddContactDialog(
            state = addDialogState,
            onAddContactClick = { lastName, firstName, middleName ->
                viewModel.event(UiEvent.AddContact(lastName, firstName, middleName))
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    snackbarHostState: SnackbarHostState,
    contactStates: List<ContactState>,
    onAddNewContactClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_main_screen),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNewContactClick,
                modifier = Modifier.padding(16.dp),
                shape = MaterialTheme.shapes.large,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.action_add_new_contact),
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(contactStates) { contactState ->
                ContactCard(
                    state = contactState,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Preview
@Composable
private fun MainPreview() {
    ExampleTheme {
        val contactStates = listOf(
            ContactState(
                model = Contact(),
                lastName = "Иванов",
                name = "Иван Иванович",
            ),
            ContactState(
                model = Contact(),
                lastName = "Петров",
                name = "Пётр Петрович",
            ),
            ContactState(
                model = Contact(),
                lastName = "Хлебников",
                name = "Багет Франциевич",
            ),
        )
        val snackbarHostState = remember { SnackbarHostState() }
        MainContent(
            contactStates = contactStates,
            snackbarHostState = snackbarHostState,
            onAddNewContactClick = {},
        )
    }
}
