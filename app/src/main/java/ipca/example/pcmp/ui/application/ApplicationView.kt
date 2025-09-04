@file:OptIn(ExperimentalMaterial3Api::class)

package ipca.example.pcmp.ui.application

    import android.annotation.SuppressLint
    import android.content.Context
    import android.net.Uri
    import android.provider.OpenableColumns
    import androidx.activity.compose.rememberLauncherForActivityResult
    import androidx.activity.result.contract.ActivityResultContracts
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.SnackbarHost
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Close
    import androidx.compose.material.rememberScaffoldState
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.OutlinedTextField
    import androidx.compose.material3.Text
    import androidx.compose.material3.TopAppBar
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.NavHostController
    import kotlinx.coroutines.launch
    import androidx.compose.material.*
    import com.google.firebase.firestore.FirebaseFirestore


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun CandidaturaView(
        viewModel: ApplicationViewModel = viewModel(),
        navController: NavHostController
    ) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                val fileName = getFileNameFromUri(context, uri)
                viewModel.onCVSelected(fileName = fileName, uri = uri)
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text("Candidatura") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.Close, contentDescription = "Fechar")
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = viewModel.firstName,
                    onValueChange = { viewModel.firstName = it },
                    label = { Text("Primeiro Nome") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = viewModel.lastName,
                    onValueChange = { viewModel.lastName = it },
                    label = { Text("Apelido") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.countryCode,
                        onValueChange = { viewModel.countryCode = it },
                        label = { Text("Indicativo") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = viewModel.contactNumber,
                        onValueChange = { viewModel.contactNumber = it },
                        label = { Text("Número") },
                        modifier = Modifier.weight(2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                OutlinedTextField(
                    value = viewModel.message,
                    onValueChange = { viewModel.message = it },
                    label = { Text("Mensagem") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 4
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                        .clickable {
                            launcher.launch("*/*") // Opens the file picker for any file type
                        }
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = viewModel.cvFileName.ifEmpty { "Clique para anexar documento" },
                        color = Color.Gray
                    )
                }

                Button(
                    onClick = {
                        viewModel.uploadCVAndSubmitApplication(context) // This will upload the CV and submit the data to Firestore
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Candidatura enviada com sucesso!")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
                ) {
                    Text("Enviar Candidatura", color = Color.White)
                }

            }
        }
    }

    fun getFileNameFromUri(context: Context, uri: Uri): String {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    return it.getString(nameIndex)
                }
            }
        }
        return uri.path?.substringAfterLast("/") ?: "Documento Selecionado"
    }
