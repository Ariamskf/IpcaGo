
package ipca.example.pcmp.ui.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ipca.example.pcmp.ui.theme.PCMPTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
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
            ),
        contentAlignment = Alignment.Center
    ) {
        val viewModel: LoginViewModel = viewModel()
        val state = viewModel.state

        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Circular Logo with Text
            Box(
                modifier = Modifier
                    .size(150.dp), // Adjust size of the circle
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.matchParentSize()
                ) {
                    // Draw the green circle
                    drawCircle(
                        color = Color(0xFF2A9C8E), // Green color for the circle
                        radius = size.minDimension / 2
                    )
                }
                // Add text inside the circle
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "IPCA",
                        fontSize = 45.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = state.email.orEmpty(),
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = { Text(text = "Inserir email institucional") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
//                    textColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray,
//                    placeholderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.password.orEmpty(),
                onValueChange = viewModel::onPasswordChange,
                placeholder = { Text(text = "Inserir palavra-passe") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
//                    textColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.LightGray,
//                    placeholderColor = Color.LightGray
                )
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.login(
                        onLoginSuccess = { navController.navigate("home") }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A9D8F)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(text = "Iniciar Sessão", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("Candidature") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A9D8F)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(text = "Torna-te um Docente", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            state.error?.let {
                Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
            }

            if (state.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    PCMPTheme{
        LoginView(navController = rememberNavController())
    }
}
