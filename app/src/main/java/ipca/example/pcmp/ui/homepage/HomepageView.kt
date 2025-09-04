package ipca.example.pcmp.ui.homepage


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ipca.example.pcmp.R
import ipca.example.pcmp.ui.components.MyBottomBar
import ipca.example.pcmp.ui.components.MyTopAppBar
import ipca.example.pcmp.ui.courses.CourseDetailsPopup
import ipca.example.pcmp.ui.login.LoginViewModel
import ipca.example.pcmp.ui.theme.PCMPTheme

@Composable
fun HomepageView(
    navController: NavHostController,
    HomepageViewModel: HomepageViewModel = viewModel()
) {
    val notificationCount = remember { mutableStateOf(3) }
    val showPopup = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                navController = navController,
                notificationCount = notificationCount.value
            )
        },
        bottomBar = { MyBottomBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Horários Section
            item {
                Text(
                    text = "Horários",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF7EC4B8)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar),
                                contentDescription = "Horário Icon",
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Introdução à Computação Gráfica\nGuimarães (AM)",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_push_pin_24),
                                contentDescription = "Afixar",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate("Schedules") },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .height(35.dp)
                                .align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFFFFF)
                            )
                        ) {
                            Text(
                                text = "Ver Horário Completo",
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            // Cursos Section
            item {
                Text(
                    text = "Os meus Cursos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    items(listOf(
                        "Aplicações Móveis" to "Guimarães",
                        "Desenvolvimento Web e Multimédia" to "Vila Verde",
                        "Design Gráfico" to "Braga"
                    )) { (title, location) ->
                        CourseCard(
                            title = title,
                            location = location,
                            onClick = { showPopup.value = true }
                        )
                    }
                }
            }

            // Notícias Section
            item {
                Text(
                    text = "Notícias",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            item {
                Card(
                    onClick = { navController.navigate("News") },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            navController.navigate("News")
                    }
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.noticia),
                            contentDescription = "Notícia",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.height(200.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Últimas",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_bookmark_24),
                                contentDescription = "Guardar"
                            )
                        }
                    }
                }
            }
        }
    }
    if (showPopup.value) {
        CourseDetailsPopup(onDismissRequest = { showPopup.value = false })
    }
}



@Composable
fun CourseCard(title: String, location: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(180.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF7EC4B8)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_push_pin_24),
                contentDescription = "Afixar",
                modifier = Modifier.align(Alignment.End),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "TESP",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = location,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .height(30.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = "Detalhes",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomepageViewPreview() {
    PCMPTheme {
        HomepageView(navController = rememberNavController())
    }
}
