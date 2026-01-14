package com.manish.login_register_qr.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBarDefaults.InputField
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.manish.login_register_qr.modal.LoginViewModal
import com.manish.login_register_qr.modal.QRViewModal
import com.manish.login_register_qr.modal.generateQrFromInputs
import com.manish.login_register_qr.ui.theme.BackgroundDark
import com.manish.login_register_qr.ui.theme.BorderDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 1. MUST use 'val' here for data classes
// 2. This is now a Class, so navigator.push(QR_GeneratorScreen(vm))
//    is an object creation, not a Composable call.
class QR_GeneratorScreen(
    private val viewModel: QRViewModal
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var showDialog by remember { mutableStateOf(false) }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {

            // ===== LOGOUT (TOP RIGHT) =====
            Button(
                onClick = { navigator.push(LoginScreen) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text("Logout", color = Color.White)
            }

            // ===== MAIN CONTENT =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 48.dp)
                    .verticalScroll(rememberScrollState()), // pushes content below logout
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ===== HEADING =====
                Text(
                    text = "QR Generator",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // ===== INPUTS =====
                OutlinedTextField(
                    value = viewModel.empID?.toString() ?: "",
                    onValueChange = { viewModel.empID = it.toIntOrNull() },
                    label = { Text("Employee ID") },
                    singleLine = true,
                    isError = viewModel.emperror != null,
                    supportingText = {
                        viewModel.emperror?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )

                OutlinedTextField(
                    value = viewModel.firstName,
                    onValueChange = { viewModel.firstName = it },
                    label = { Text("First Name") },
                    singleLine = true,
                    isError = viewModel.firstnameerror != null,
                    supportingText = {
                        viewModel.firstnameerror?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )
                )

                OutlinedTextField(
                    value = viewModel.middleName,
                    onValueChange = { viewModel.middleName = it },
                    label = { Text("Middle Name") },
                    singleLine = true,
                    isError = viewModel.middlenameerror != null,
                    supportingText = {
                        viewModel.middlenameerror?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )
                )

                OutlinedTextField(
                    value = viewModel.lastName,
                    onValueChange = { viewModel.lastName = it },
                    label = { Text("Last Name") },
                    singleLine = true,
                    isError = viewModel.lastnameerror != null,
                    supportingText = {
                        viewModel.lastnameerror?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )
                )

                OutlinedTextField(
                    value = viewModel.phoneNo,
                    onValueChange = { viewModel.phoneNo = it },
                    label = { Text("Phone") },
                    singleLine = true,
                    isError = viewModel.phonenoerror != null,
                    supportingText = {
                        viewModel.phonenoerror?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )            )

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    isError = viewModel.emailerror != null,
                    supportingText = {
                        viewModel.emailerror?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    )
                )


                // ===== GENERATE BUTTON =====
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (viewModel.isValid()) {
                            viewModel.qrBitmap = generateQrFromInputs(
                                viewModel.empID,
                                viewModel.firstName,
                                viewModel.middleName,
                                viewModel.lastName,
                                viewModel.phoneNo,
                                viewModel.email
                            )
                            showDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BackgroundDark)
                ) {
                    Text("Generate QR Code")
                }


            }
        }
        if (showDialog && viewModel.qrBitmap != null) {
            AlertDialog(

                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "Generated QR Code",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            bitmap = viewModel.qrBitmap!!.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(220.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BackgroundDark),
                        ) {
                        Text("OK",
                        )
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            showDialog = false
                        },

                        ) {
                        Text("Close")
                    }
                }
            )
        }


    }
}
