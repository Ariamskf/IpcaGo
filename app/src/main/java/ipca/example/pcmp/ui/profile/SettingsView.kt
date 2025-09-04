package ipca.example.pcmp.ui.profile

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import ipca.example.pcmp.R

@Composable
fun SettingsView(viewModel: SettingsViewModel, onClose: () -> Unit, navController: NavController) {
    val context = LocalContext.current
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Definições",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

            }

            // Settings Options
            val context = LocalContext.current
            SettingsOption("Alterar Dados", Icons.Default.Person) {
                navController.navigate("ChangeDataView")
            }
            SettingsOption("Favoritos", Icons.Default.FavoriteBorder) { viewModel.onFavorites() }
            SettingsOption("Notificações", Icons.Default.Notifications) {
                viewModel.onNotifications(context)
            }

            // Dark Mode Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable { viewModel.toggleDarkMode() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Modo Escuro",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
                Switch(
                    checked = viewModel.isDarkModeEnabled,
                    onCheckedChange = { viewModel.toggleDarkMode() }
                )
            }

            // Divider and Information Section
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Informação",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            SettingsOption("Versão", Icons.Default.Info) { viewModel.onVersionInfo() }
            SettingsOption("Termos e Condições", Icons.Default.Info) { viewModel.onTermsAndConditions() }
            SettingsOption("Política de Privacidade", Icons.Default.Info) { viewModel.onPrivacyPolicy() }

            Spacer(modifier = Modifier.weight(1f))

            // Logout Button
            Button(
                onClick = { viewModel.onLogout()
                          navController.navigate("login") {
                              popUpTo("login") { inclusive = true }
                          }
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00796B),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Terminar Sessão",
                    fontSize = 16.sp
                )
            }

            // Footer
            Image(
                painter = painterResource(id = R.drawable.nex_solutions),
                contentDescription = "Nex Solutions Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 60.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit
            )
        }
    }
}
@Composable
fun SettingsOption(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color(0xFF00796B),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsView() {
    val mockViewModel = object : SettingsViewModel() {
        override val isDarkModeEnabled: Boolean = false
        override fun toggleDarkMode() {}
        override fun onEditData(  ) {}
        override fun onFavorites() {}
        override fun onNotifications(context: Context) {
            try {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                context.startActivity(intent)
                Log.d("SettingsView", "Navigating to Notification Settings")
            } catch (e: Exception) {
                Log.d("SettingsView", "Error navigating to Notification Settings", e)
            }
        }
        override fun onVersionInfo() {}
        override fun onTermsAndConditions() {}
        override fun onPrivacyPolicy() {}
        override fun onLogout() {}
    }

    SettingsView(viewModel = mockViewModel, onClose = {} , navController = NavHostController(LocalContext.current))
}

