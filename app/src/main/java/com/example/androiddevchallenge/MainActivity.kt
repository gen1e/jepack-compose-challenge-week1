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
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.androiddevchallenge.model.Cat
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}


fun retrieveCatList(): ArrayList<Cat> {
    //TODO: replace with cat api call
    //Dummy data of cats
    val cats = ArrayList<Cat>()
    cats.add(Cat(0,"Bob", 100))
    cats.add(Cat(1,"Loki", 4))
    cats.add(Cat(2,"Ham", 10))
    cats.add(Cat(3,"Joob", 5))
    cats.add(Cat(4,"Joob", 5))
    cats.add(Cat(5,"Joob", 5))
    cats.add(Cat(6,"Joob", 5))
    cats.add(Cat(7,"Joob", 5))
    cats.add(Cat(8,"Joob", 5))
    cats.add(Cat(9,"Joob", 5))
    cats.add(Cat(10,"Joob", 5))
    cats.add(Cat(11,"Joob", 5))
    cats.add(Cat(12,"Joob", 5))
    cats.add(Cat(13,"Joob", 5))
    return cats
}

// Start building your app here!
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val cats = retrieveCatList()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            Surface(color = MaterialTheme.colors.background) {
                catList(cats, navController)
            }
        }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            Log.d("ARGH", "NavHost id: ${backStackEntry.arguments?.getInt("userId")}")
            catDetail(cat = cats[backStackEntry.arguments?.getInt("userId")!!], navController = navController) }
    }

}

@Composable
fun catList(cats: List<Cat>, navController: NavController) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(cats) { cat ->
            catCondensed(cat = cat, navController)
        }

    }
}

@Composable
fun catCondensed(cat: Cat, navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                Log.d("ARGH", "catCondensed: Click to detail/${cat.id}")
                navController.navigate("detail/${cat.id}")
            }) {
        Image(
            painter = painterResource(id = R.drawable.ic_default_photo),
            contentDescription = "Picture of ${cat.name}",
            Modifier.size(100.dp, 100.dp)
        )
        Column {
            Text(text = "Name: ${cat.name}")
            Text(text = "Age: ${cat.age}")
        }
    }
}

@Composable
fun catDetail(cat: Cat, navController: NavController?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            Icons.Filled.ArrowBack, "back",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    navController!!.popBackStack()
                }
                .padding(12.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_default_photo),
            contentDescription = "Picture of ${cat.name}",
            Modifier.size(200.dp, 200.dp)
        )
        Text(
            text = "${cat.name} ${cat.age}",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.descriptionDefault),
            fontSize = 14.sp
        )
    }
}

@Preview("Cat detail", widthDp = 360, heightDp = 640)
@Composable
fun CatDetailPreview() {
    MyTheme {
        Surface(color = MaterialTheme.colors.background) {
            catDetail(cat = Cat(0,"Bob", 100), null)
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
