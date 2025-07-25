
package com.example.nur_eins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var screen by remember { mutableStateOf(0) }

            Scaffold(
                bottomBar = {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(onClick = { screen = 0 }) { Text("Ratender") }
                        Button(onClick = { screen = 1 }) { Text("Wort schreiben") }
                    }
                }
            ) {
                when (screen) {
                    0 -> RatenderScreen()
                    1 -> WortSchreibenScreen()
                }
            }
        }
    }
}

@Composable
fun RatenderScreen() {
    var start by remember { mutableStateOf(false) }
    var countdown by remember { mutableStateOf(5) }
    var showWord by remember { mutableStateOf(false) }

    LaunchedEffect(start) {
        if (start) {
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            showWord = true
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (!start) {
            Button(onClick = {
                start = true
                countdown = 5
                showWord = false
            }) {
                Text("Los")
            }
        } else if (!showWord) {
            Text("$countdown...", fontSize = 40.sp)
        } else {
            Text("Apfel", fontSize = 60.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun WortSchreibenScreen() {
    var word1 by remember { mutableStateOf("") }
    var word2 by remember { mutableStateOf("") }
    var step by remember { mutableStateOf(1) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (step) {
            1 -> {
                OutlinedTextField(value = word1, onValueChange = { word1 = it }, label = { Text("Wort 1") })
                Spacer(Modifier.height(16.dp))
                Button(onClick = { if (word1.isNotBlank()) step = 2 }) { Text("OK") }
            }
            2 -> {
                OutlinedTextField(value = word2, onValueChange = { word2 = it }, label = { Text("Wort 2") })
                Spacer(Modifier.height(16.dp))
                Button(onClick = { if (word2.isNotBlank()) step = 3 }) { Text("Vergleichen") }
            }
            3 -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(word1, fontSize = 30.sp, modifier = Modifier.clickable { word2 = ""; step = 1 })
                    Text(word2, fontSize = 30.sp, modifier = Modifier.clickable { word1 = ""; step = 1 })
                }
            }
        }
    }
}
