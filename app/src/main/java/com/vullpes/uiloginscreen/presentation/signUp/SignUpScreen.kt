package com.vullpes.uiloginscreen.presentation.signUp

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vullpes.uiloginscreen.presentation.Routes
import com.vullpes.uiloginscreen.presentation.login.LoginFormEvent
import com.vullpes.uiloginscreen.presentation.login.LoginViewModel


@Composable
fun SignUpScreen(
    navControler: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    var passwordVisibility = remember { mutableStateOf(false) }


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            navControler.navigate(Routes.QrcodeScreen.route)
        } else {
            // Permission Denied: Do something
            Toast.makeText(context, "Conceder permissão de camera", Toast.LENGTH_SHORT).show()
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
                is LoginViewModel.ValidationEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Scaffold() { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 3 / 4)
                .clip(RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp))
                .background(Color.Cyan)
                .padding(paddingValues)
        )



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = state.user,
                onValueChange = { viewModel.onEvent(SignUpEvent.UsuarioChanged(it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                isError = state.userError != null,
                label = { Text("Usuário") },
            )
            if (state.userError != null) {
                Text(
                    text = state.userError,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 37.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(SignUpEvent.EmailChanged(it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                isError = state.emailError != null,
                label = { Text("E-mail") },
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 37.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(SignUpEvent.PasswordChanged(it)) },
                isError = state.passwordError != null,
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
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 37.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = state.confirmPassword,
                onValueChange = { viewModel.onEvent(SignUpEvent.ConfirmPasswordChanged(it)) },
                singleLine = true,
                isError = state.confirmPasswordError != null,
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
                label = { Text(text = "Confirmar Senha") },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if (state.confirmPasswordError != null) {
                Text(
                    text = state.confirmPasswordError,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 37.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                viewModel.onEvent(SignUpEvent.Submit)
            }, enabled = state.buttonEnabled) {
                Text(text = "Sign up")
            }

        }
    }

}