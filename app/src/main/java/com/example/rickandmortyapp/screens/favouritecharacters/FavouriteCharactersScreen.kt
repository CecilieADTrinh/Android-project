package com.example.rickandmortyapp.screens.favouritecharacters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickandmortyapp.R

@Composable
fun FavouriteCharactersScreen(favouriteCharactersViewModel: FavouriteCharactersViewModel) {
    val favCharacters by favouriteCharactersViewModel.favCharacters.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.rickexcited),
            contentDescription = "Rick excited",
            modifier = Modifier.size(200.dp)
        )

        Row { // Legger favorittikon ved teksten horisontalt
            Text(
                text = "Favorite Characters",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(119, 145, 120),
                modifier = Modifier.padding(16.dp)
            )
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite icon",
                tint = Color(94, 128, 95),
            )
        }
        LazyColumn {
            items(favCharacters) { favCharacter ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(150.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(119, 145, 120)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp)
                        ) {

                            Text(
                                text = favCharacter.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Text(
                                text = "ID: ${favCharacter.id}",
                                fontSize = 14.sp,
                                color = Color.White
                            )

                            Text(
                                text = "Gender: ${favCharacter.gender}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Status: ${favCharacter.status}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Species: ${favCharacter.species}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }

                        AsyncImage( // For at bildet skal ligge til høyre
                            model = favCharacter.image,  // Bildets URL
                            contentDescription = "Image of ${favCharacter.name}",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        IconButton(
                            onClick = {
                                favouriteCharactersViewModel.removeFromFavorites(favCharacter)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite icon",
                                tint = Color.Red,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}





