package com.tufar.androidstudyjam4

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tufar.androidstudyjam4.ui.theme.AndroidStudyJam4Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStudyJam4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigasyonSayfa()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Islemler(modifier: Modifier = Modifier) {

    var sayi1 by remember { mutableStateOf("") }
    var sayi2 by remember { mutableStateOf("") }
    var sonuc by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "4 işlem",
            fontSize = 55.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = sonuc,
            fontSize = 35.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            value = sayi1,
            onValueChange = { sayi1 = it },
            label = {
                Text(
                    text = "Birinci Sayıyı Giriniz"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = sayi2,
            onValueChange = { sayi2 = it },
            label = {
                Text(
                    text = "İkinci Sayıyı Giriniz"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(50.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Button(onClick = {
                var result = sayi1.toInt() + sayi2.toInt()
                sonuc = result.toString()
            }) {
                Text(text = "Topla")
            }
            Button(onClick = {
                var result = sayi1.toInt() - sayi2.toInt()
                sonuc = result.toString()
            }) {
                Text(text = "Çıkar")
            }
            Button(onClick = {
                var result = sayi1.toInt() * sayi2.toInt()
                sonuc = result.toString()
            }) {
                Text(text = "Çarp")
            }
            Button(onClick = {
                var result = sayi1.toInt() / sayi2.toInt()
                sonuc = result.toString()
            }) {
                Text(text = "Böl")
            }
        }



    }
}


@Composable
fun NavigasyonSayfa(){
    var navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "anasayfa"
    ){
        composable("anasayfa"){
            Anasayfa(navController)
        }
        composable("tahmin_sayfasi"){
            TahminSayfasi(navController)
        }
        composable("sonuc_sayfasi/{result}", arguments = listOf(
            navArgument("result"){
                type = NavType.BoolType
            }
        )){
            val result = it.arguments?.getBoolean("result")
            SonucSayfasi(navController, result)
        }
    }

}

@Composable
fun Anasayfa(navController: NavController, modifier: Modifier = Modifier){

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Sayı Tahmin Oyunu",
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            lineHeight = 50.sp
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = {
            navController.navigate("tahmin_sayfasi")
        }) {
            Text(
                text = "Tahmin Sayfasına Geç",
                fontSize = 25.sp
            )
            
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TahminSayfasi(navController: NavController, modifier: Modifier = Modifier){

    var tahmin by remember{mutableStateOf("")}
    var kalanHak by remember{mutableStateOf(5)}
    var yakinlik by remember{mutableStateOf("")}
    var rastgeleSayi by remember{mutableStateOf(5)}


    var buttonOnClick = {
        kalanHak -= 1
        var tahminSayisi = tahmin.toInt()

        if (kalanHak == 0){
            navController.navigate("sonuc_sayfasi/false"){
                popUpTo("oyun_sayfasi"){inclusive = true}
            }
        }

        if(tahminSayisi == rastgeleSayi){
            navController.navigate("sonuc_sayfasi/true"){
                popUpTo("oyun_sayfasi"){inclusive = true}
            }
        }

        if(rastgeleSayi < tahminSayisi){
            yakinlik = "Azalt"
        }
        if(rastgeleSayi > tahminSayisi){
            yakinlik = "Artır"
        }

        tahmin = ""
    }

    LaunchedEffect(key1 = true){
        rastgeleSayi = Random.nextInt(101)
        Log.e("Random Number is:", rastgeleSayi.toString())
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Tahmin Sayfası",
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            lineHeight = 50.sp
        )
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            Column {
                Text(
                    text = "Kalan Hak: $kalanHak",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "Yakınlık: $yakinlik",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            value = tahmin,
            onValueChange = {tahmin = it},
            label = {Text(text = "Tahmininizi Giriniz")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(50.dp))
        
        Button(onClick = buttonOnClick ) {
            Text(text = "Tahmin Et")

        }
    }
}

@Composable
fun SonucSayfasi(navController: NavController, result: Boolean?, modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        if(result!!){
            Text(
                text = "Kazandınız",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
        }else{
            Text(
                text = "Kaybettiniz",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidStudyJam4Theme {
        var navController = rememberNavController()
        SonucSayfasi(navController, false)
    }
}