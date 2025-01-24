package com.example.clothtroop.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clothtroop.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IntroScreen(
                onClick = {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            )
        }
    }
}

@Composable
@Preview
fun IntroScreen(onClick:()->Unit={}) {
    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = null,
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.fashion),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        Image(
            painter = painterResource(id = R.drawable.title),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
        Image(
            painter = painterResource(id = R.drawable.go),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .clickable { onClick() }
        )
    }
}
