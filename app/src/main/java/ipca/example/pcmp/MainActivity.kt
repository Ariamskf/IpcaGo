package ipca.example.pcmp



import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.example.pcmp.ui.news.NewsView
import ipca.example.pcmp.ui.news.shareNews
import ipca.example.pcmp.ui.schedules.ScheduleView
import ipca.example.pcmp.ui.application.CandidaturaView
import ipca.example.pcmp.ui.homepage.HomepageView
import ipca.example.pcmp.ui.inquiry.InquiryView
import ipca.example.pcmp.ui.inquiry.inquiryViewModel
import ipca.example.pcmp.ui.login.LoginView
import ipca.example.pcmp.ui.login.SplashScreen
import ipca.example.pcmp.ui.notifications.NotificationView
import ipca.example.pcmp.ui.profile.ChangeDataView
import ipca.example.pcmp.ui.profile.ProfileView
import ipca.example.pcmp.ui.profile.ProfileViewModel
import ipca.example.pcmp.ui.profile.SettingsView
import ipca.example.pcmp.ui.profile.SettingsViewModel
import ipca.example.pcmp.ui.theme.PCMPTheme
import ipca.example.pcmp.ui.ucs.UcDetailsViewModel
import ipca.example.pcmp.ui.ucs.ucDetailsView
import ipca.example.pcmp.ui.ucs.ucsView
import ipca.example.pcmp.ui.ucs.ucsViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val navController = rememberNavController()
            PCMPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "SplashScreen"
                    ) {
                        composable("SplashScreen") {
                            SplashScreen(navController = navController)
                        }

                        composable("login") {
                            LoginView(navController = navController)
                        }
                        composable("home") {
                            HomepageView(navController = navController)
                        }

                        composable("candidature") {
                            CandidaturaView(navController = navController)
                        }

                        composable("notifications") {
                            NotificationView(navController = navController)
                        }
                        composable("Schedules") {
                            ScheduleView(navController = navController)
                        }

                        composable("ucs") {
                            ucsView(navController = navController, viewModel = ucsViewModel())
                        }

                        composable("News") {
                            NewsView(
                                navController = navController,
                                onBackClick = { navController.popBackStack() },
                                onBookmarkClick = { println("Bookmark clicked") },
                                onCommentClick = { println("Comment clicked") },
                                onLikeClick = { println("Like clicked") },
                                onShareClick = { context, newsTitle ->
                                    shareNews(
                                        context,
                                        newsTitle
                                    )
                                },
                            )
                        }

                        composable("profile") {
                            ProfileView(
                                navController = navController,
                                viewModel = ProfileViewModel()
                            )
                        }

                        composable("ucDetails") {
                            ucDetailsView(
                                context = LocalContext.current,
                                viewModel = UcDetailsViewModel(),
                                onClose = { navController.popBackStack() }
                            )
                        }

                        composable("inquery") {
                            InquiryView(
                                navController = navController,
                                viewModel = inquiryViewModel()
                            )
                        }

                        composable("settings") {
                            SettingsView(
                                navController = navController,
                                viewModel = SettingsViewModel(),
                                onClose = { navController.navigateUp() })
                        }

                        composable("ChangeDataView") {
                            ChangeDataView(
                                onClose = { navController.navigateUp() },
                                navController = navController
                            )
                        }
                    }
                }
                @Composable
                fun MyApp(viewModel: SettingsViewModel) {
                    val isDarkTheme = viewModel.isDarkModeEnabled
                    MaterialTheme(
                        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
                    ) {
                        // Pass the viewModel to the settings screen
                        SettingsView(
                            viewModel = viewModel,
                            onClose = { /* handle close */ },
                            navController = rememberNavController()
                        )
                    }
                }
                LaunchedEffect(key1 = true) {
                    val auth = Firebase.auth
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}
