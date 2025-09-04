package ipca.example.pcmp.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ipca.example.pcmp.R
import ipca.example.pcmp.models.Screen


@Composable
fun MyBottomBar(navController: NavHostController) {
    val screens = listOf(
        Screen("home", "Início", R.drawable.baseline_home_24),
        Screen("Schedules", "Horários", R.drawable.baseline_calendar_today_24),
        Screen("ucs", "Uc's", R.drawable.baseline_menu_book_24),
        Screen("profile", "Perfil", R.drawable.baseline_account_circle_24)
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination


    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color(0xFF3AB397),
        modifier = Modifier.shadow(elevation = 8.dp)
    ) {
        screens.forEach { screen ->

            val isSelected = currentDestination?.route == screen.route


            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.ImageVector),
                        contentDescription = screen.title,
                        tint = if (isSelected) Color(0xFF008066) else Color(0xFF008066)
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        color = if (isSelected) Color(0xFF008066) else Color(0xFF008066)
                    )
                },
                selected = isSelected,

                onClick = {

                    navController.navigate( screen.route) {
                        // Avoid multiple copies of the same destination in the back stack
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true // Save the current state to restore it when navigating back
                        }
                        launchSingleTop = true // Prevent multiple instances of the same destination
                        restoreState = true    // Restore the previous state if it exists
                    }
                }
                ,
                alwaysShowLabel = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomBarPreview() {
    val navController = rememberNavController()
    MyBottomBar(navController = navController)
}


