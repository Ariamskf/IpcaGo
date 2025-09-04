package ipca.example.pcmp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.example.pcmp.R

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavController, notificationCount: Int) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ipca_pt), // Replace with your logo resource ID
                        contentDescription = "IPCA Logo",
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                }
                Spacer(modifier = Modifier.weight(1.5f))
                NotificationIcon(
                    notificationCount = notificationCount,
                    onNotificationClick = { navController.navigate("notifications") },
                    iconSize = 30.dp
                )
            }
        }
    )
}

@Composable
fun NotificationIcon(notificationCount: Int, onNotificationClick: () -> Unit, iconSize: Dp = 24.dp) {
    Box(contentAlignment = Alignment.TopEnd) {
        IconButton(onClick = { onNotificationClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_notifications_24), // Replace with your notification icon resource ID
                contentDescription = "Notifications",
                tint = Color.Black,
                modifier = Modifier.size(iconSize)
            )
        }
        if (notificationCount > 0) {
            Box(
                modifier = Modifier
                    .size((iconSize / 2).coerceAtLeast(16.dp))
                    .background(Color(0xFFF44336), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = notificationCount.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = (iconSize.value / 3).sp
                    )
                )
            }
        }
    }
}

@Composable
fun NotificationsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        // Add your notifications list here
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val notificationCount = remember { mutableStateOf(2) } // Example notification count

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MyTopAppBar(
                navController = navController,
                notificationCount = notificationCount.value
            )
        }
        composable("notifications") {
            NotificationsScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTopAppBarPreview() {
    val navController = rememberNavController()
    MyTopAppBar(navController = navController, notificationCount = 2)
}
