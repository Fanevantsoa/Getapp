package com.tco.getscan
//import java.lang.reflect.Modifier
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
    @Composable
    fun SplashScreen(navController: NavController){
        // Attente de 5seconde avant de montrer l'ecran principal
        LaunchedEffect(key1 = true){
            delay(5000L)
            navController.navigate("main_screen")
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id =R.drawable.logo) ,
                    contentDescription = null ,modifier = Modifier
                        .width(300.dp)
                        .height(300.dp)
                )
                Text(text = "GET 2024", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            }
            Image(
                painter = painterResource(id = R.drawable.bott),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
            )


        }
    }
    @Preview
// Fonction de navigation qui crée l'interface de navigation
    @Composable
    fun Navigation(){

        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "splash_screen"
        ){
            composable("splash_screen"){
                SplashScreen(navController = navController)
            }
            composable("main_screen"){
                MainScreen(navController = navController)

            }
        }
    }
    @Composable
    fun MainScreen(navController: NavController){
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    color = Color(android.graphics.Color.parseColor("#ffffff"))
                ),
        ) {

            Image(painter = painterResource(id = R.drawable.espa),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center,
                modifier = Modifier
                    //.width(400.dp)
                    .height(450.dp)
            )

            Text("Bienvenu dans GET 2024 Application!",
                fontSize = 30.sp,
                fontFamily = FontFamily.Cursive,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start=20.dp, end=20.dp, top=20.dp, bottom=20.dp),
                color = Color(android.graphics.Color.parseColor("#152E21"))
            )

            Button(onClick = {
                startActivity(Intent(this@MainActivity, QrScanActivity::class.java))
            },
                Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),

                colors = ButtonDefaults.buttonColors(
                    Color(android.graphics.Color.parseColor("#3F6D73"))
                ),
                shape = RoundedCornerShape(50)
            )
            {
                Text(
                    text = "Scan QR Code",
                    color = Color.White,
                    fontSize = 18.sp,
                )
            }

            Button(onClick = {
                val navigate = Intent(this@MainActivity,EtudiantActivity::class.java)
                startActivity(navigate)
            },
                Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(android.graphics.Color.parseColor("#3F6D73"))
                ),
                shape = RoundedCornerShape(50)
            )
            {
                Text(text = "Contrôle des étudiants",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Button(onClick = {finishAndRemoveTask()}, Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(android.graphics.Color.parseColor("#D90909"))
                ),
                shape = RoundedCornerShape(50)
            )
            {
                Text(
                    text = "Quitter",
                    color = Color.White,
                    fontSize = 18.sp,
                )
            }
        }
    }
}
class QrScanActivity : ComponentActivity(){
    private var textResult = mutableStateOf("")

    private val barCodeLauncher = registerForActivityResult(ScanContract()){
            result ->
        if (result.contents == null) {
            Toast.makeText(this@QrScanActivity, "Cancelled", Toast.LENGTH_SHORT).show()
        }
        else {
            textResult.value = result.contents
            var text = textResult.value
            //Toast.makeText(this@QrScanActivity, "${text}" , Toast.LENGTH_SHORT).show()
            getStdPresence(text)
        }
    }

    private fun showCamera(){
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan a QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        barCodeLauncher.launch(options)

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
            isGranted ->
        if (isGranted){
            showCamera()
        }
        else {
            Toast.makeText(this@QrScanActivity, "Permission refusée", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            checkCameraPermission(this@QrScanActivity)
            //var text = textResult.value
            //Toast.makeText(this@QrScanActivity, "${text}" , Toast.LENGTH_SHORT).show()
            QrScanCode()
        }
    }
    @Composable
    fun QrScanCode() {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.qr_scan),
                    modifier = Modifier.size(100.dp),
                    contentDescription = "QR"
                )
                Box(modifier = Modifier
                    .padding(16.dp)
                    .border(1.dp, Color.Black))
                {
                    Text(
                        text = textResult.value,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
    }
    private fun checkCameraPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED){
            showCamera()
        }
        else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun getStdPresence(inputString:String){
        // Create retrofit instance
        val retrofitData = Retrofit.Builder()
            .baseUrl("https://flask-api-espa.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val id = inputString.substringBefore(" ")
        val name = inputString.substringAfter(" ")

        // Call weather api
        val result = retrofitData.getOneStudentPresence(id)

        result.enqueue(object: Callback<List<DataStudentPresence>>{
            override fun onResponse(call: Call<List<DataStudentPresence>>, response: Response<List<DataStudentPresence>>) {
                if (response.isSuccessful){
                    val rlt = response.body()!!
                    if (rlt.isEmpty()){
                        Toast.makeText(this@QrScanActivity, "Nothing" , Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val mydata = rlt[0]
                        val identity = mydata.id
                        val presence = mydata.presence

                        val liste = listOf(name, identity," OK")
                        textResult.value = liste.toString()
                        //Toast.makeText(this@QrScanActivity, "id: ${identity}, name: ${name}, presence ${presence}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<DataStudentPresence>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error on the server", Toast.LENGTH_SHORT).show()
                Log.e("GetScan", "Error on the server", t)
            }
        })
    }
}
