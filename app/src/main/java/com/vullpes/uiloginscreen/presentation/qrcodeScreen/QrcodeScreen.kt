package com.vullpes.uiloginscreen.presentation.qrcodeScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vullpes.uiloginscreen.R
import com.vullpes.uiloginscreen.presentation.Routes

@Composable
fun QrcodeScreen(
    navControler: NavHostController,
    viewModel: QrcodeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val state = viewModel.state
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is QrcodeViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Registrado com êxito",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is QrcodeViewModel.ValidationEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }

    Scaffold(
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(padding)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.Cyan)
            ) {

                CameraPreview() { barcode ->
                    viewModel.onEvent(QrcodeEvent.QrcodeCaptured(barcode))
                }

            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Card(modifier = Modifier.fillMaxWidth(), elevation = 6.dp) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navControler.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, "Voltar")
                        }
                        Text(
                            text = stringResource(R.string.leitor_qrcode),
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                        )
                        TextButton(onClick = { navControler.navigate(Routes.ListQrcodeScreen.route) }) {
                            Text(text = "Bipados")
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Qrcode Captured",
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (state.qrcode != "") state.qrcode else "",
                        style = TextStyle(fontSize = 16.sp)
                    )
                }


            }


        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(padding)
        ) {
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter),
                onClick = {
                    viewModel.onEvent(QrcodeEvent.SaveQrcode)
                }) {
                Text(text = "Salvar qrcode capturado")
            }
        }
    }

}


