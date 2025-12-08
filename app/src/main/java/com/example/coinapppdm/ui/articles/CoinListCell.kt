package com.example.coinapppdm.ui.articles
import com.example.coinapppdm.domain.models.Coin
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun CoinListCell(
    coin: Coin,
    modifier : Modifier = Modifier,
    onClick: () -> Unit,
){
    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable{
                onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ){
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Text(text = coin.name ?: "",
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 20.sp)
            AsyncImage(
                model = coin.image,
                contentDescription = coin.name
            )
        }
    }

}
