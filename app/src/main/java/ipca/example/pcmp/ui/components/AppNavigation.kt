package ipca.example.pcmp.ui.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.example.pcmp.ui.schedules.CalendarScreen
import ipca.example.pcmp.ui.schedules.ScheduleView
import ipca.example.pcmp.ui.courses.CourseDetailsPopup
import ipca.example.pcmp.ui.homepage.HomepageView
import ipca.example.pcmp.ui.inquiry.InquiryView
import ipca.example.pcmp.ui.login.LoginView
import ipca.example.pcmp.ui.login.SplashScreen
import ipca.example.pcmp.ui.notifications.NotificationView
import ipca.example.pcmp.ui.profile.ChangeDataView
import ipca.example.pcmp.ui.profile.ProfileView
import ipca.example.pcmp.ui.profile.ProfileViewModel
import ipca.example.pcmp.ui.profile.SettingsView
import ipca.example.pcmp.ui.profile.SettingsViewModel
import ipca.example.pcmp.ui.ucs.ucsView
import ipca.example.pcmp.ui.ucs.ucsViewModel

@Composable
fun AppNav() {
    val navController = rememberNavController()


    NavHost(navController, startDestination = "SplashScreen") {
        composable("SplashScreen") {
            SplashScreen(navController = navController, viewModel = viewModel())
        }

        composable("login") {
            LoginView(navController = navController)
        }
    }
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomepageView(navController = navController)
        }
        composable("detalhes") {
            CourseDetailsPopup(onDismissRequest = { navController.navigateUp() })
        }
        composable("schedule") {
            ScheduleView(navController = navController)
        }
        composable("ucs") {
            ucsView(navController = navController, viewModel = ucsViewModel())
        }
        composable("profile") {
            ProfileView(navController = navController, viewModel = ProfileViewModel())
        }
    }

    NavHost(navController = navController, startDestination = "schedule") {
        composable("schedule") {
            ScheduleView(
                navController = navController,
                ScheduleViewModel = viewModel()
            )
        }
        composable("schedules") {
            CalendarScreen(
                onDateSelected = { selectedDate ->
                    // Example: Handle the selected date
                    println("Selected date: $selectedDate")
                },
                navController = navController
            )
        }

    }

    NavHost(navController = navController, startDestination = "notifications") {
        composable("notifications") {
            NotificationView(
                navController
            )
        }
        composable("inquery") {
            InquiryView(
                navController = navController,
                viewModel = viewModel()
            )
        }

    }

    NavHost(navController = navController, startDestination = "profile") {
        composable("profile") {
            ProfileView(navController = navController, viewModel = ProfileViewModel())
        }
        composable("settings") {
            SettingsView(
                navController = navController,
                viewModel = SettingsViewModel(),
                onClose = { navController.navigateUp() })
        }
    }

    NavHost(navController = navController, startDestination = "settingsView") {
        composable("settingsView") {
            SettingsView(
                viewModel = SettingsViewModel(),
                onClose = { /* Close action */ },
                navController = navController
            )
        }
        composable("ChangeDataView") {
            ChangeDataView(onClose = { navController.popBackStack() } , navController = navController)
        }
    }

    NavHost(
        navController = navController,
        startDestination = "SettingsView"
    ) {
        composable("SettingsView") {
            SettingsView(navController = navController, viewModel = SettingsViewModel(), onClose = { /* Close action */ }) }
        composable("LoginView") { LoginView( navController = navController) } // Add your login screen composable here
    }



}

