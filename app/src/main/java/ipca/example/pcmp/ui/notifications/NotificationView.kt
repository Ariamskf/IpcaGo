package ipca.example.pcmp.ui.notifications

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ipca.example.pcmp.R
import ipca.example.pcmp.ui.components.MyBottomBar
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationView(
    navController: NavHostController,
    NotificationViewModel: NotificationViewModel = viewModel()
) {
    val allNotifications = NotificationViewModel.notifications.collectAsState()
    val selectedTab = remember { mutableStateOf("Todas") } // Default tab is "Todas"

    Scaffold(
        bottomBar = { MyBottomBar(navController = navController) },
        topBar = {
            TopAppBar(
                title = { Text("As minhas Notificações") },
                colors = TopAppBarDefaults.smallTopAppBarColors(Color.White),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Tabs for filtering notifications
            NotificationTabs(selectedTab.value) { tab ->
                selectedTab.value = tab // Update the selected tab
            }

            // LazyColumn for displaying filtered notifications
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Filter notifications based on the selected tab
                val filteredNotifications = when (selectedTab.value) {
                    "Todas" -> allNotifications.value
                    "Não Lido" -> allNotifications.value.filter { it.isUnread }
                    "Lidas" -> allNotifications.value.filter { !it.isUnread }
                    else -> allNotifications.value
                }

                // Display notifications
                items(filteredNotifications.size) { index ->
                    NotificationItem(
                        notification = filteredNotifications[index],
                        onClick = { route ->
                            route?.let { navController.navigate(it) } // Navigate if route exists
                        }
                    )
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                }

            }
        }
    }
}

// NotificationTabs Component: Displays tabs and handles tab selection
@Composable
fun NotificationTabs(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TabText("Todas", isSelected = selectedTab == "Todas", onClick = { onTabSelected("Todas") })
        TabText("Não Lido", isSelected = selectedTab == "Não Lido", onClick = { onTabSelected("Não Lido") })
        TabText("Lidas", isSelected = selectedTab == "Lidas", onClick = { onTabSelected("Lidas") })
    }
}

// TabText Component: Displays individual tab with styling
@Composable
fun TabText(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = text,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) Color(0xFF3AB397) else Color.Gray,
        modifier = Modifier.clickable { onClick() }
    )
}

// NotificationItem Component: Displays a single notification item
@Composable
fun NotificationItem(notification: Notification, onClick: (String?) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick(notification.route) }
    ) {
        // Notification Icon
        Image(
            painter = painterResource(id = notification.categoryIcon),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(end = 12.dp),
            contentScale = ContentScale.Fit
        )

        // Notification Content
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = notification.title,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.date,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        // Unread Indicator
        if (notification.isUnread) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .align(Alignment.CenterVertically)
                    .background(Color(0xFF3AB397), CircleShape)
            )
        }
    }
}

// Preview of the NotificationView
@Preview(showBackground = true)
@Composable
fun NotificationViewPreview() {
    val mockNotifications = listOf(
        Notification(
            title = "Nova atualização disponível",
            description = "Atualize seu aplicativo para a versão mais recente.",
            date = "15 Jan 2025",
            isUnread = true,
            categoryIcon = R.drawable.notification1
        ),
        Notification(
            title = "Evento programado",
            description = "Participe do evento de introdução à computação gráfica.",
            date = "14 Jan 2025",
            isUnread = false,
            categoryIcon = R.drawable.notification2
        )
    )
    val viewModel = object : NotificationViewModel() {
        override val notifications = MutableStateFlow(mockNotifications)
    }
    NotificationView(
        navController = rememberNavController(),
        NotificationViewModel = viewModel
    )
}

