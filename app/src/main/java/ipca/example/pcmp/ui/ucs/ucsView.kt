package ipca.example.pcmp.ui.ucs


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ipca.example.pcmp.R
import ipca.example.pcmp.ui.components.MyBottomBar


@Composable
fun ucsView(viewModel: ucsViewModel, navController: NavHostController) {
    val allUnits by viewModel.units.collectAsState() // Collect all units
    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") } // State to hold the search query
    var selectedFilter by remember { mutableStateOf<Filter?>(null) } // State to hold the selected filter

    // Apply both search and filter logic
    val filteredUnits = allUnits.filter { unit ->
        (searchQuery.isEmpty() || unit.name.contains(searchQuery, ignoreCase = true) || unit.semester.contains(searchQuery, ignoreCase = true)) &&
                (selectedFilter == null || selectedFilter!!.matches(unit))
    }

    Scaffold(
        bottomBar = { MyBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Logo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ipca_pt),
                    contentDescription = "IPCA Logo",
                    modifier = Modifier
                        .height(65.dp)
                        .padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "As minhas Unidades \nCurriculares",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Search Bar with Filters
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    modifier = Modifier.weight(1f),
                    query = searchQuery,
                    onQueryChange = { searchQuery = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { /* Show filter dialog */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                        contentDescription = "Filter",
                        tint = Color(0xFF008066)
                    )
                }
                IconButton(onClick = { /* Handle sort action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_sort_24),
                        contentDescription = "Sort",
                        tint = Color(0xFF008066)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of Units
            if (filteredUnits.isNotEmpty()) {
                filteredUnits.forEach { unit ->
                    CurricularUnitCard(unit = unit, navController)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            } else {
                Text(
                    text = "Nenhuma unidade encontrada.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

// Filter Logic
data class Filter(val semester: String?) {
    fun matches(unit: ucs): Boolean {
        return semester == null || unit.semester == semester
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, query: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Procurar") },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .height(48.dp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF00695C)
            )
        }
    )
}

@Composable
fun CurricularUnitCard(unit: ucs , navController: NavHostController) {
    Card(
        onClick = { navController.navigate("ucDetails") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable {
                navController.navigate("ucDetails")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Circular Progress
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0F2F1))
            ) {
                CircularProgressIndicator(
                    progress = unit.progress / 100f,
                    modifier = Modifier.size(45.dp),
                    color = Color(0xFF00695C),
                    strokeWidth = 5.dp
                )
                Text(
                    text = "${unit.progress}%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00695C)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Unit Information
            Column {
                Text(
                    text = unit.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = unit.semester,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun ucsViewPreview() {
    ucsView(viewModel = ucsViewModel(), navController = rememberNavController())
}

