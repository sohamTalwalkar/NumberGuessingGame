package com.example.numberguessinggame

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberguessinggame.ui.theme.NumberGuessingGameTheme
import kotlin.math.absoluteValue
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GuessingApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GuessingApp(){
    var guess by remember { mutableStateOf<Int>(1) }
    var randNum by remember { mutableStateOf(Random.nextInt(1, 100)) }
    var textFieldValue by remember { mutableStateOf("") }
    var i by remember { mutableStateOf(0) }
    val keyboard = LocalSoftwareKeyboardController.current
    var endText by remember { mutableStateOf(false) }
    val activity = (LocalContext.current as? Activity)

    fun Result(): String{
            if (randNum==guess){
                endText=true
            }
            else if(randNum>guess){
                return "Your number is less than the computer's number."
            }
            else if(randNum<guess){
                return "Your number is more than the computer's number."
            }
        return "No condition has matched so far."
    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFF0E7986)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Number Guessing Game",
                style = TextStyle(fontSize = 25.sp, fontStyle = FontStyle.Normal),
                color = Color.Cyan,
                modifier = Modifier.padding(20.dp)
            )
        }
        Column(modifier = Modifier
            .padding(17.dp)) {
            Text(
                text = "\nInstructions:\n\n" +
                        "1] You have to guess the number which is decided by the computer.\n" +
                        "2] You will be given a range of 1 to 100, in between whom you have to guess the number.\n" +
                        "3] The more accurately you guess the number the less will be your error count.\n",
                color = Color(0xFF0E7986),
                style = TextStyle(fontSize = 15f.sp)
            )
        }
        Column (modifier = Modifier
            .height(180.dp)
            .padding(17.dp)
            .fillMaxWidth()
        ,horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Enter the number:")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = textFieldValue, onValueChange = {
                newValue->textFieldValue=newValue
                keyboard?.show()
            })
            Button(onClick = {
                keyboard?.hide()
                guess=textFieldValue.toInt()
                ++i
            }) {
                Text(text = "Enter")
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color(0xFF83D8E2))
            ,horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = Result(),
                color = Color(0xFF0E7986),
                style = TextStyle(fontSize = 25f.sp),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
        if(endText){
            AlertDialog(onDismissRequest = { /*TODO*/ }) {
                Column (
                    modifier = Modifier
                        .height(280.dp)
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text =  "You have guessed the number with $i errors!",
                        color = Color(0xFF0E7986),
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(12.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Do you want to exit the app or want to play the game again?",
                        color= Color(0xFF83D8E2),
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(12.dp),
                        textAlign = TextAlign.Center
                    )
                    Row (modifier = Modifier.
                        fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Button(onClick = {activity?.finish() }) {
                            Text(text = "Exit")
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(onClick = { endText=false; randNum=Random.nextInt(1,100); i=0; textFieldValue="" }) {
                            Text(text = "Play Again")
                        }
                    }
                }
            }
        }
    }
}
