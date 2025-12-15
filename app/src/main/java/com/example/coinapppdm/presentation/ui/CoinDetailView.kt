package com.example.coinapppdm.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.coinapppdm.domain.model.Coin
import com.example.coinapppdm.presentation.viewmodel.CoinDetailViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CoinDetailView(coin: Coin) {

    val priceFormatted = "€${"%.2f".format(coin.currentPrice)}"
    val changeFormatted = "%.2f %%".format(coin.priceChangePercentage24h)
    val isPositive = coin.priceChangePercentage24h >= 0
    val changeColor = if (isPositive) Color(0xFF2E7D32) else Color(0xFFC62828)

    val viewModel : CoinDetailViewModel = hiltViewModel()
    val uiState = viewModel.uiState

    LaunchedEffect(key1 = coin.id) {
        viewModel.loadNote(coin.id)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    model = coin.image,
                    contentDescription = coin.name,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )

                coin.symbol?.let { symbol ->
                    Text(
                        text = symbol.uppercase(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Divider()

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Preço atual",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                Text(
                    text = priceFormatted,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Variação (24h)",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                Text(
                    text = changeFormatted,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = changeColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.note,
                    onValueChange = { viewModel.onNoteChange(it)},
                    label = {Text("Nota sobre esta moeda")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                val isSaving = uiState.isSaving
                val isNoteEmpty = uiState.note.isBlank()
                Button(
                    onClick = { viewModel.saveNote(coin.id)},
                    modifier = Modifier.fillMaxWidth(),

                    enabled = !isSaving && !isNoteEmpty
                ){
                    if(isSaving){
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                    }else{
                        Text("Guardar Nota")
                    }
                }

                uiState.error?.let {
                    Text(
                        text = "Erro: $it",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                if(uiState.saved){
                    Text(
                        text = "Nota guardada",
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        }
    }
}
