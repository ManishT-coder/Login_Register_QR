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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.manish.login_register_qr.database.service.RegisterService
import com.manish.login_register_qr.modal.RegisterViewModal
import com.manish.login_register_qr.ui.theme.BackgroundDark
import com.manish.login_register_qr.ui.theme.BorderDark
import com.manish.login_register_qr.ui.theme.Purple40
import com.manish.login_register_qr.ui.theme.PurpleGrey40
import com.manish.login_register_qr.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

object RegisterScreen : Screen {
    private fun readResolve(): Any = RegisterScreen

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = remember(context) {
            RegisterViewModal(RegisterService(context))
        }

        RegisterView(viewModel, navigator)
    }

    @Composable
    private fun RegisterView(
        viewModel: RegisterViewModal,
        navigator: Navigator
    ) {
        val scope = rememberCoroutineScope()
        var popupMessage by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }


        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
                .verticalScroll(rememberScrollState()), // pushes content below logout

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(20.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(24.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )


                // Common color config for TextFields to avoid duplication
                val textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = Purple40,
                    unfocusedBorderColor = PurpleGrey40
                )

                OutlinedTextField(
                    value = viewModel.firstName,
                    onValueChange = { viewModel.firstName = it },
                    label = { Text("First Name", color = BorderDark) },
                    singleLine = true,
                    isError = viewModel.firstNameError != null,
                    supportingText = {
                        viewModel.firstNameError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.middleName,
                    onValueChange = { viewModel.middleName = it },
                    label = { Text("Middle Name", color = BorderDark) },
                    singleLine = true,
                    isError = viewModel.middleNameError != null,
                    supportingText = {
                        viewModel.middleNameError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },

                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.lastName,
                    onValueChange = { viewModel.lastName = it },
                    label = { Text("Last Name", color = BorderDark) },
                    singleLine = true,
                    isError = viewModel.lastNameError != null,
                    supportingText = {
                        viewModel.lastNameError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.username,
                    onValueChange = { viewModel.username = it },
                    label = { Text("Username", color = BorderDark) },
                    singleLine = true,
                    isError = viewModel.usernameError != null,
                    supportingText = {
                        viewModel.usernameError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.phoneNo,
                    onValueChange = { viewModel.phoneNo = it },
                    label = { Text("Phone Number", color = BorderDark) },
                    singleLine = true,
                    isError = viewModel.phoneNoError != null,
                    supportingText = {
                        viewModel.phoneNoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
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
                    colors = textFieldColors,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            val success = viewModel.register()
                            if (success) {
                                popupMessage = "Account Created Successfully! Go ahead to login 👍"
                                showDialog = true
                                delay(3000)
                                navigator.push(LoginScreen)
                                viewModel.clearFields()
                            } else {
                                popupMessage = "Registration Failed!"
                                showDialog = true
                            }

                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BackgroundDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Register", color = Color.White)
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },

                        title = {
                            Text(
                                text = if (popupMessage.contains("Successfully"))
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
                    text = "I have an account? Login",
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        navigator.push(LoginScreen)
                    }
                )
                }

            }
        }
    }
