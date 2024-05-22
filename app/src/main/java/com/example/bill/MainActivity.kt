package com.example.bill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bill.ui.theme.BillTheme
import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bill.reader.data.toXml
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BillTheme {

                FilePickerForm()
                //Scaffold(modifier = Modifier.fillMaxSize()) {
                    //innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )


                //}
            }
        }
    }
}


@Composable
fun FilePickerForm() {
    var selectedFileName by remember { mutableStateOf("No file selected") }
    var fileContent by remember { mutableStateOf("") }

    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    fileContent = reader.readText()
                    selectedFileName = uri.path ?: "Unknown file"

                    var mystring = fileContent.toString()
                    var str = ""

                    toXml.toReadXML(uri.path.toString(),mystring)

                }
            }
        }
    )

    Column(
        modifier = Modifier.padding(top = 50.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        Text(text = selectedFileName)
        Button(onClick = {
            //application/xml
            filePickerLauncher.launch(arrayOf("text/xml"))
        }) {
            Text("Select XML File")
        }
        if (fileContent.isNotEmpty()) {

            Text(text = "File Content:")
            Text(text = fileContent)
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BillTheme {
        Greeting("Android")
    }
}