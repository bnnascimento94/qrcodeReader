package com.vullpes.uiloginscreen.presentation.login

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vullpes.uiloginscreen.presentation.Routes


//@Preview
@Composable
fun LoginScreen(
     navHostController: NavHostController,
     viewModel: LoginViewModel = hiltViewModel()
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            navHostController.navigate(Routes.QrcodeScreen.route)
        } else {
            // Permission Denied: Do something
            Toast.makeText(context,"Conceder permissão de camera", Toast.LENGTH_SHORT).show()
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }

    val state = viewModel.state
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> {
                    launcher.launch(Manifest.permission.CAMERA)
                }
                is LoginViewModel.ValidationEvent.Error ->{
                    Toast.makeText(
                        context,
                        event.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    Scaffold() {  padding ->

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 3 / 4)
                .clip(RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp))
                .background(Color.Cyan)
                .padding(padding)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var passwordVisibility = remember { mutableStateOf(false) }

            Text(
                text = "Qrcode Reader",
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 26.sp
                ),
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )

            OutlinedTextField(
                value = state.usuario,
                onValueChange = { viewModel.onEvent(LoginFormEvent.EmailChanged(it)) },
                isError = state.errorUsuario != null,
                label = { Text(text = "E-mail") },
                singleLine = true,
                placeholder = { Text(text = "example@example.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            if (state.errorUsuario != null) {
                Text(
                    text = state.errorUsuario,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 37.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = state.senha,
                onValueChange = { viewModel.onEvent(LoginFormEvent.PasswordChanged(it))},
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Show Password"
                        )
                    }
                },
                label = { Text(text = "Senha") },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if (state.errorSenha != null) {
                Text(
                    text = state.errorSenha,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 37.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.padding(horizontal = 50.dp)) {
                OutlinedButton(
                    onClick = {
                        viewModel.onEvent(LoginFormEvent.Submit)
                    },
                    enabled = viewModel.enabledLoginButton(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(6.dp))
                ) {
                    Text(text = "Logar")
                }

                OutlinedButton(
                    onClick = {
                         navHostController.navigate(Routes.SignUpScreen.route)
                    },

                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(6.dp))
                ) {
                    Text(text = "Cadastrar")
                }
            }
        }

    }


}