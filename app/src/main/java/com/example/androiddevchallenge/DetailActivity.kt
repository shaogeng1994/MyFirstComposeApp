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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androiddevchallenge.model.Puppy
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.gray666
import com.example.androiddevchallenge.ui.theme.typography
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        setContent {
            MyTheme(darkTheme = false) {
                MyApp(
                    intent.getSerializableExtra("data") as Puppy,
                    onBackPressed = {
                        onBackPressed()
                    },
                    viewModel
                )
            }
        }
    }

    companion object {
        fun newIntent(context: Context, puppy: Puppy) = Intent(context, DetailActivity::class.java).apply {
            putExtra("data", puppy)
        }
    }
}

class DetailViewModel : ViewModel() {

    val _isAdopt = MutableLiveData(false)

    val isAdopt: LiveData<Boolean>
        get() = _isAdopt

    fun onAdoptChanged(newAdopt: Boolean) {
        _isAdopt.value = newAdopt
    }
}

@Composable
fun MyApp(puppy: Puppy, onBackPressed: () -> Unit, viewModel: DetailViewModel) {
    val adopt: Boolean by viewModel.isAdopt.observeAsState(initial = false)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        topBar = {
            getTopBar(title = "Puppy Adoption", onBackPressed)
        },
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(4)
                )
            }
        }
    ) {

        Surface(
            Modifier
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .background(color = MaterialTheme.colors.background)
                    .padding(12.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Image(
                    painter = painterResource(id = puppy.img), "",
                    Modifier
                        .size(80.dp, 80.dp)
                        .clip(RoundedCornerShape(50))
//                        .padding(20.dp)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop

                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = puppy.name, color = Color.Black, style = typography.body1)
                Spacer(modifier = Modifier.size(10.dp))

                Column(
                    Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(10.dp)
                        .fillMaxWidth(),
                ) {
                    Text(text = "Varieties:${puppy.varieties}", color = gray666, style = typography.body2)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(text = "Character:${puppy.character}", color = gray666, style = typography.body2)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(text = "Release Date:${puppy.createTime}", color = gray666, style = typography.body2)
                    Spacer(modifier = Modifier.size(5.dp))
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = {
                        viewModel.onAdoptChanged(true)
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("adopt successful!")
                        }
                    },
                    enabled = !adopt
                ) {
                    Text(text = "adopt")
                }
            }
        }
    }
}

@Composable
fun getTopBar(title: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Text(title)
        },
        Modifier.fillMaxWidth(),
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null, // decorative element
                Modifier
                    .padding(16.dp)
                    .clickable {
                        onBackPressed.invoke()
                    },
                tint = Color.White
            )
        }
    )
}
