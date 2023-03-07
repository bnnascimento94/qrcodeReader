package com.vullpes.uiloginscreen.presentation.listQrcode


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vullpes.uiloginscreen.presentation.components.QrcodeListItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListQrcodeScreen(
    navControler: NavHostController,
    viewModel: ListQrcodeViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ListQrcodeViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Deletado com Sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ListQrcodeViewModel.ValidationEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text("Bipados")
                },
                backgroundColor = MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = { navControler.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retornar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(ListFormEvent.DeleteAllQrcodes)
                    }) {
                        Icon(Icons.Filled.Delete, "Deletar")
                    }
                }

            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isloading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            if (state.qrcodes.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        modifier = Modifier.size(128.dp),
                        imageVector = Icons.Filled.List,
                        contentDescription = "No Itens"
                    )
                    Text(text = "No Items", style = TextStyle(fontSize = 20.sp))
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(items = state.qrcodes, key = { savedQrcode ->
                        savedQrcode.qrcodeID!!
                    })
                    { qrcode ->
                        val state = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    viewModel.onEvent(ListFormEvent.DeleteQrcodeById(qrcode.qrcodeID))
                                }
                                true
                            }
                        )

                        SwipeToDismiss(state = state, background = {
                            val colord = when (state.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                                    .background(color = colord)

                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Gray,
                                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 5.dp)
                                )
                            }
                        },
                        dismissContent = {
                            QrcodeListItem(qrcode = qrcode) {}
                        },
                            directions = setOf(DismissDirection.EndToStart)
                        )
                    }

                }
            }
        }
    }
}