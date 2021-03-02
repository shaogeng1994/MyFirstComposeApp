/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.model.Puppy
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme(darkTheme = false) {
                MyApp { puppy ->
                    startActivity(DetailActivity.newIntent(this, puppy))
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(onSelected: (Puppy) -> Unit) {
    Scaffold(
        Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        topBar = {
            getTopBar(title = "Puppy Adoption")
        },

        ) {
        Feed(feedItems = arrayListOf(
            Puppy("Harry", "unknown", "Active and cheerful", R.drawable.puppy1, "2021/3/1"),
            Puppy("Jerry", "Kirky", "Lively and sticky", R.drawable.puppy2, "2021/2/19"),
            Puppy("Katrina", "Husky", "Meekness", R.drawable.puppy3, "2021/2/1")
        ), onSelected = onSelected)
    }
}

@Composable
fun getTopBar(title: String) {
    TopAppBar(
        title = {
            Text(title)
        },
        Modifier.fillMaxWidth())
}

@Composable
fun Feed(
    feedItems: List<Puppy>,
    onSelected: (Puppy) -> Unit
) {
    LazyColumn(
        Modifier
            .background(color = MaterialTheme.colors.background)
            .padding(12.dp, 12.dp, 12.dp, 0.dp)
            .fillMaxSize()
    ) {
        items(feedItems.size, itemContent = { index ->
            PuppyItem(feedItems[index], onSelected)
            Spacer(Modifier.size(12.dp))
        })
    }
}

@Composable
fun PuppyItem(puppy: Puppy, onSelected: (Puppy) -> Unit) {
    Column(
        Modifier
            .clickable(onClick = {
                onSelected.invoke(puppy)
            })
            .clip(Shapes().small)
            .background(color = Color.White)
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column() {
//            Text(text = puppy.name, fontSize = 16.sp, color = Color.Black)
            Text(text = puppy.name, color = Color.Black, style = typography.body1)
            Spacer(Modifier.size(6.dp))
            Text(text = "${puppy.createTime} release", fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.size(6.dp))
            Image(
                painter = painterResource(id = puppy.img),
                "",
                Modifier.height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp { puppy ->

        }
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp{ puppy ->

        }
    }
}
