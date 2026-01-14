package com.manish.login_register_qr.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.manish.login_register_qr.database.service.LoginService
import com.manish.login_register_qr.modal.LoginViewModal
import com.manish.login_register_qr.modal.QRViewModal
import com.manish.login_register_qr.ui.theme.BackgroundDark
import com.manish.login_register_qr.ui.theme.BorderDark
import com.manish.login_register_qr.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object LoginScreen : Screen {
    private fun readResolve(): Any = LoginScreen

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = remember(context) { LoginViewModal(LoginService(context)) }

        LoginView(viewModel, navigator)
    }

    @Composable
    private fun LoginView(viewModel: LoginViewModal, navigator: Navigator) {
        val scope = rememberCoroutineScope()
        var showDialog by remember { mutableStateOf(false) }
        var popupMessage by remember { mutableStateOf("") }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(20.dp)

                    // Using PurpleGrey40 from your theme for the border
                    .border(2.dp, color = Color.Black, shape = RoundedCornerShape(24.dp))
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), // pushes content below logout

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "LOGIN",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextSecondary, // Uses Purple40 or Purple80 automatically
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                OutlinedTextField(
                    value = viewModel.username,
                    onValueChange = { viewModel.username = it },
                    label = { Text("Username", color = BorderDark) },
                    singleLine = true,
                    isError = viewModel.usernameError != null,
                    supportingText = {
                        viewModel.usernameError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = { Text("Email", color = BorderDark) },
                    singleLine = true,
                    isError = viewModel.emailError != null,
                    supportingText = {
                        viewModel.emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            val success = viewModel.login()
                            if (success) {
                                popupMessage = "Welcome Back ${viewModel.username}"
                                showDialog = true
                                delay(3000)
                                viewModel.clearFields()
                                navigator.push(QR_GeneratorScreen(viewModel= QRViewModal())
                                )
                            } else {
                                popupMessage = "Login Failed! please fill correct information"
                                showDialog = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BackgroundDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login", color = Color.White)
                }

                // Show popup when showDialog is true
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },

                        title = {
                            Text(
                                text = if (popupMessage.contains("Welcome Back"))
                                    "Success"
                                else
                                    "Error"
                            )
                        },

                        text = {
                            Text(text = popupMessage)
                        },

                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                    if (popupMessage.contains("Successfully")) {
                                        navigator.push(LoginScreen)
                                        viewModel.clearFields()
                                    }
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Don't have an account? Sign up",
                    color = Color.Black, // Your custom Extra color
                    modifier = Modifier.clickable { navigator.push(RegisterScreen) }
                )
            }
        }
    }
}