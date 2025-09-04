package ipca.example.pcmp.ui.profile


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import ipca.example.pcmp.R
import ipca.example.pcmp.ui.components.MyBottomBar
import java.io.File
import java.io.FileOutputStream

@Composable
fun ProfileView( navController: NavHostController, viewModel: ProfileViewModel) {

        Scaffold(
            topBar = { ProfileTopBar( navController) },
            bottomBar = { MyBottomBar( navController = navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Header Section
                ProfileHeader( )

                Spacer(modifier = Modifier.height(50.dp))

                // Dashboards Section
                Text(
                    text = "Dashboards",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Dashboard Cards
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        EnhancedDashboardCard(
                            title = "UC'S",
                            subtitle = "Novembro 2024",
                            icon = Icons.Default.Home,
                            data = listOf("PCMA", "PIP", "Computação Gráfica", "L. Programação"),
                            progress = listOf(0.6f, 0.8f, 0.5f, 0.7f)
                        )
                    }
                    item {
                        EnhancedDashboardCard(
                            title = "Alunos",
                            subtitle = "Ano Letivo 2024/25",
                            icon = Icons.Default.Person,
                            data = listOf("AM", "SEC", "DWM", "RSI"),
                            progress = listOf(0.4f, 0.6f, 0.7f, 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(60.dp))


                ScheduleMediaCard( )
            }
        }
    }

@Composable
fun EnhancedDashboardCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    data: List<String>,
    progress: List<Float>
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(180.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE3F2FD), // Light Blue
                            Color(0xFFA6FFF7)  // Slightly Darker Blue
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header with Icon
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color(0xFF009688),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = subtitle,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                // Title
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                // Pie Charts with Labels
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    progress.zip(data).forEach { (progressValue, label) ->
                        PieChartWithLabel(
                            progress = progressValue,
                            label = label
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PieChartWithLabel(progress: Float, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(
            modifier = Modifier.size(60.dp)
        ) {
            val sweepAngle = progress * 360f
            drawArc(
                color = Color(0xFF009688), // Pie chart color
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = true
            )
            drawArc(
                color = Color(0xFF76AFA9), // Background color
                startAngle = -90f + sweepAngle,
                sweepAngle = 360f - sweepAngle,
                useCenter = true
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.Gray
        )
    }

}



    @Composable
    fun ScheduleMediaCard() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFE8F5E9), // Light Green
                                Color(0xFFC8E6C9)  // Slightly Darker Green
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Header
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Horário Médio",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "+02h / semana",
                            color = Color(0xFF2E7D32),
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Bar Chart
                    BarChart(
                        data = listOf(15f, 11f, 8f, 12f, 10f, 7f, 5f), // Example data
                        labels = listOf("S", "T", "Q", "Q", "S", "S", "D")
                    )
                }
            }
        }
    }

@Composable
fun ProfileTopBar( navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Handle back action */ }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(text = "Perfil", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        IconButton(onClick = {navController.navigate("settings")}) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
        }
    }
}
@Composable
fun ProfileHeader() {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Profile Picture
            Image(
                painter = if (selectedImageUri != null) {
                    rememberAsyncImagePainter(model = selectedImageUri)
                } else {
                    painterResource(R.drawable.teacher)
                },
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable { openPhotoPicker(context, launcher) }
            )


            Spacer(modifier = Modifier.width(16.dp))

            // Profile Details
            Column {
                Text(
                    text = "Sofia",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "sofiacosta@ipca.pt",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "Nº Docente: 1055",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "Change Photo",
                    modifier = Modifier
                        .clickable { openPhotoPicker(context, launcher) }
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }
}

    @Composable
    fun BarChart(data: List<Float>, labels: List<String>) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            data.zip(labels).forEach { (value, label) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height((value * 5).dp) // Dynamic bar height
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF388E3C))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = label, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
fun openPhotoPicker(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val intent = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
    }
    launcher.launch(intent)
}

fun saveImageLocally(context: Context, uri: Uri): Uri {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, "profile_picture.jpg")
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    return Uri.fromFile(file)
}


@Preview (showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileView( navController = NavHostController(LocalContext.current), viewModel = ProfileViewModel())
}