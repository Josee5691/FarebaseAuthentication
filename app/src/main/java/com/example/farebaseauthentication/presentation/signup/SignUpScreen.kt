package com.example.farebaseauthentication.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.farebaseauthentication.R
import kotlinx.coroutines.launch

@Composable
fun RegisterContent(
    email:String, password: String,
    passVisibility:Boolean,
    onEmailChange:(String)->Unit,
    onPassChange:(String)->Unit, onPass2Change: (String)->Unit,
    onIconClick: ()->Unit, onRegister: ()->Unit,
    state: State<SignUpState?>,
    modifier: Modifier
) {
    val icon =
        if (passVisibility) painterResource(id = R.drawable.open) else painterResource(id = R.drawable.hidden)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TextField(
            value = email,
            onValueChange = onEmailChange,
            leadingIcon = {
                painterResource(id = R.drawable.user)
            },
            label = { Text(text = "Email") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
        )

        Spacer(modifier = Modifier.padding(16.dp))

        TextField(value = password,
            onValueChange = onPassChange,
            leadingIcon = {
                painterResource(id = R.drawable.pass_key)
            }, label = { Text(text = "Password") }, shape = RoundedCornerShape(8.dp),
            singleLine = true, trailingIcon = {
                IconButton(onClick = onIconClick) {
                    Icon(painter = icon, contentDescription = "pass visibility")
                }
            },
            visualTransformation = if (passVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        Spacer(modifier = Modifier.padding(16.dp))

        TextField(value = password,
            onValueChange = onPass2Change,
            leadingIcon = {
                painterResource(id = R.drawable.pass_key)
            }, label = { Text(text = "Confirm password") }, shape = RoundedCornerShape(8.dp),
            singleLine = true, trailingIcon = {
                IconButton(onClick = onIconClick) {
                    Icon(painter = icon, contentDescription = "pass visibility")
                }
            },
            visualTransformation = if (passVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = onRegister, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.padding(16.dp))


        Row {
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }
        }

    }
}

@Composable
fun SignUpScreen(modifier: Modifier,viewModel: SignUpViewModel = hiltViewModel()){
    var email by rememberSaveable{ mutableStateOf("") }
    var password by rememberSaveable{ mutableStateOf("") }
    var password2 by rememberSaveable{ mutableStateOf("") }
    var passwordVisibility by rememberSaveable{ mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)

    RegisterContent(
        email = email,
        password = password,
        passVisibility = passwordVisibility,
        onEmailChange = {email=it},
        onPassChange = {password=it},
        onPass2Change = {password2=it},
        onIconClick = { passwordVisibility=!passwordVisibility },
        onRegister = { scope.launch {
            viewModel.registerUser(email, password)
        } },
        state = state,
        modifier = modifier
    )

    LaunchedEffect(key1 = state.value?.isSuccess){
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true){
                val success = state.value?.isSuccess
                Toast.makeText(context, "$success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(key1 = state.value?.isError){
        scope.launch {
            if (state.value?.isError?.isNotEmpty() == true){
                val error = state.value?.isError
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}