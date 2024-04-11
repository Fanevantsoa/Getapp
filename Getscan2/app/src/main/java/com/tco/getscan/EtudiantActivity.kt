package com.tco.getscan
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tco.getscan.ui.theme.GetscanTheme
import com.tco.getscan.MainActivity

class EtudiantActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            register()
        }
    }
}
@Preview
@Composable
fun register(){
    val context = LocalContext.current
    val itemsList= listOf("Payement effectué", "Payement non effectué")
    val selectedItem= remember {
        mutableListOf(itemsList[0])
    }
    Column(
        Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(android.graphics.Color.parseColor("#ffffff"))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.olona),
                contentDescription = null, modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
            Text(text = "Contrôle des Etudiants ", fontSize = 30.sp, fontFamily = FontFamily.Cursive, fontWeight = FontWeight.Bold)


            LazyColumn(modifier = Modifier.background(Color.LightGray)){}
            //FORMULAIRE
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = "", onValueChange = {},
                label = { Text(text = "ID Imatriculation")})
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = "", onValueChange = {},
                label = { Text(text = "Nom et Prénom")})

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = "", onValueChange = {},
                label = { Text(text = "Date de Naissance")})

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = "", onValueChange = {},
                label = { Text(text = "Grade")}, placeholder = { Text(text = "ghjhj")})


            Spacer(modifier = Modifier.height(10.dp))
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),

            ){
                Button(onClick = { /*TODO*/ }, Modifier

                    .height(66.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp),

                    colors = ButtonDefaults.buttonColors(
                        Color(android.graphics.Color.parseColor("#3F6D73"))
                    ),
                    shape = RoundedCornerShape(50)
                )
                {
                    Text(text = "Envoyer",
                        color = Color.White,
                        fontSize = 18.sp,
                        )
                }
            }


        }
    }
}

