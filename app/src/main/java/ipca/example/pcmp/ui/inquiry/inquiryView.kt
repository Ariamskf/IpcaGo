@file:Suppress("UNREACHABLE_CODE")

package ipca.example.pcmp.ui.inquiry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ipca.example.pcmp.ui.components.BottomPicker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InquiryView(navController: NavHostController, viewModel: inquiryViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Inquérito Ano Letivo 2025/26",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val user = viewModel.user

            OutlinedTextField(
                value = user.firstName,
                onValueChange = { viewModel.updateFirstName(it) },
                label = { Text("Primeiro Nome") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                singleLine = true
            )

            OutlinedTextField(
                value = user.lastName,
                onValueChange = { viewModel.updateLastName(it) },
                label = { Text("Apelido") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                singleLine = true
            )

            OutlinedTextField(
                value = user.preferredUcs,
                onValueChange = { viewModel.updatePreferredUcs(it) },
                label = { Text("Escreva quais as UC’s que gostaria de lecionar?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                maxLines = 5
            )

            // Campus Picker
            var showCampusPicker by remember { mutableStateOf(false) }
            val campusSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            val scope = rememberCoroutineScope()

            OutlinedTextField(
                value = user.selectedCampus,
                onValueChange = {},
                label = { Text("Selecione o Polo de Preferência") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir Menu",
                        modifier = Modifier.clickable { showCampusPicker = true }
                    )
                }
            )

            if (showCampusPicker) {
                ModalBottomSheet(
                    onDismissRequest = { showCampusPicker = false },
                    sheetState = campusSheetState
                ) {
                    BottomPicker(
                        title = "Selecione o Polo de Preferência",
                        options = viewModel.campuses,
                        onOptionSelected = {
                            viewModel.updateSelectedCampus(it)
                            scope.launch { campusSheetState.hide() }.invokeOnCompletion {
                                if (!campusSheetState.isVisible) {
                                    showCampusPicker = false
                                }
                            }
                        }
                    )
                }
            }

            // Hours Picker
            var showHoursPicker by remember { mutableStateOf(false) }
            val hoursSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

            OutlinedTextField(
                value = user.selectedHours,
                onValueChange = {},
                label = { Text("Selecione a Disposição de Horas") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir Menu",
                        modifier = Modifier.clickable { showHoursPicker = true }
                    )
                }
            )

            if (showHoursPicker) {
                ModalBottomSheet(
                    onDismissRequest = { showHoursPicker = false },
                    sheetState = hoursSheetState
                ) {
                    BottomPicker(
                        title = "Selecione a Disposição de Horas",
                        options = viewModel.hours,
                        onOptionSelected = {
                            viewModel.updateSelectedHours(it)
                            scope.launch { hoursSheetState.hide() }.invokeOnCompletion {
                                if (!hoursSheetState.isVisible) {
                                    showHoursPicker = false
                                }
                            }
                        }
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = user.termsAccepted,
                    onCheckedChange = { viewModel.updateTermsAccepted(it) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Aceito os termos e condições.", style = MaterialTheme.typography.bodySmall)
            }

            if (viewModel.errorMessage.isNotBlank()) {
                Text(
                    text = viewModel.errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Button(
                onClick = { viewModel.submitForm() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = user.termsAccepted,
                colors = ButtonDefaults.buttonColors(Color(0xFF008066))
            ) {
                Text("Enviar Candidatura", color = Color.White)
            }

            LaunchedEffect(viewModel.submissionSuccess) {
                if (viewModel.submissionSuccess) {
                    navController.navigate("notifications")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InquiryViewPreview() {
    InquiryView(navController = rememberNavController() , viewModel = inquiryViewModel())
}