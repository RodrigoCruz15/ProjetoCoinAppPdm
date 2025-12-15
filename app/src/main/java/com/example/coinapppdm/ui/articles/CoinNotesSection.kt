package com.example.coinapppdm.ui.articles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coinapppdm.models.Note
import com.example.coinapppdm.ui.viewModel.NotesViewModel
import kotlinx.coroutines.launch

@Composable
fun CoinNotesSection(
    coinId: String,
    notesViewModel: NotesViewModel
) {
    val notes by notesViewModel.notes
    var newNote by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    Column {
        // Lista de notas
        notes.forEach { note ->
            Text(text = note.content)
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = newNote,
            onValueChange = { newNote = it },
            label = { Text("Nova nota") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (newNote.isNotBlank()) {
                    notesViewModel.addNote(coinId, newNote)
                    newNote = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Nota")
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}
