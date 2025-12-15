package com.example.coinapppdm.ui.articles
import com.example.coinapppdm.models.Coin
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.coinapppdm.ui.theme.Red


@Composable
fun CoinListCell(
    coin: Coin,
    modifier : Modifier = Modifier,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    isFavorite: Boolean
){
    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable{ onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ){
        // 🚨 MUDANÇA PRINCIPAL: Usar Box para sobreposição e posicionamento
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp) // O padding do conteúdo move-se para aqui
        ) {

            // 1. Conteúdo Principal (Centralizado)
            Column(
                modifier = Modifier.fillMaxWidth(), // Ocupa a largura total do Box
                horizontalAlignment = Alignment.CenterHorizontally // Mantém o conteúdo centralizado
            ) {
                Text(text = coin.name ?: "",
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontSize = 20.sp)
                AsyncImage(
                    model = coin.image,
                    contentDescription = coin.name
                )
                Spacer(modifier = Modifier.height(8.dp)) // Espaçador no fundo do conteúdo, se necessário
            }


            // 2. O IconButton (Estrela)
            // 💡 PONTO CHAVE: Ancorado no canto superior direito do Box
            IconButton(
                onClick = onToggleFavorite,
                // O MODIFICADOR DE ALINHAMENTO FAZ O TRABALHO
                modifier = Modifier
                    .align(Alignment.TopEnd) // Ancorar no Topo e no Fim (Direita)
                    .offset(x = 12.dp, y = (-12).dp) // Ajuste fino para contrariar o padding do Box
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = "Toggle Favorite",
                    tint = if(isFavorite) Red else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
