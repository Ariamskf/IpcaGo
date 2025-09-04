package ipca.example.pcmp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.pcmp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController, // Navigation controller for transitioning
    viewModel: HiltViewModel = HiltViewModel()
) {
    LaunchedEffect(Unit) {
        delay(12000) // Splash screen duration
        navController.navigate("login") {
            popUpTo("splash_screen") { inclusive = true } // Remove splash from the back stack
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF007F66),
                        Color(0xFF213A44)
                    )
                )
            ), // Set background color matching your design
        contentAlignment = Alignment.Center
    ) {
        // Center Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Main Logo
            Image(
                painter = painterResource(id = R.drawable.splashscreen), // Replace with your logo resource
                contentDescription = "IPCA Logo",
                modifier = Modifier.size(300.dp) // Adjust size as needed
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        // "Powered By" Logo at the Bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp), // Adjust padding for bottom spacing
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.powered_by), // Replace with your "powered by" logo
                contentDescription = "Powered By Logo",
                modifier = Modifier.size(170.dp) // Adjust size if necessary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}
