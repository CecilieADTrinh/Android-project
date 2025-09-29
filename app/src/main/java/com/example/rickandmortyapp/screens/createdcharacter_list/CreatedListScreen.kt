package com.example.rickandmortyapp.screens.createdcharacter_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.components.CharacterItem
import com.example.rickandmortyapp.screens.showcharacters.GenderDropdown

@Composable
fun CreatedListScreen(createdListViewModel: CreatedListViewModel) { // Sender inn viewModel

    val characters by createdListViewModel.filteredCharacters.collectAsState()
    var selectedGender by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        createdListViewModel.setCharacters()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.happyrick),
            contentDescription = "Rick happy",
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = "Characters Created and Stored in Database",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(Color(43, 70, 37), Color(126, 149, 51))
                )
            ),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown-meny for filtrering
        GenderDropdown(
            selectedGender = selectedGender,
            onGenderSelected = { gender ->
                selectedGender = gender
                createdListViewModel.filterByGender(gender)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Liste over karakterer
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (characters.isEmpty()) {
                item {
                    Text(
                        text = "No characters found in the database.",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp),

                        )
                }
            } else {
                var isGreen = true
                items(characters) { character ->
                    val backgroundColor = if (isGreen) {
                        Color(199, 216, 143) // Grønn
                    } else {
                        Color(255, 249, 186) // Gul
                    }
                    isGreen = !isGreen

                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .padding(8.dp)
                            .shadow(4.dp, RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = backgroundColor
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight() // Tilpasser høyden i boksen hva man har i den
                                .padding(10.dp)
                        ) {
                            CharacterItem(
                                character = character,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}