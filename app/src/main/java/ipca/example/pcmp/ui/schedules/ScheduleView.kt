package ipca.example.pcmp.ui.schedules

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ipca.example.pcmp.ui.components.MyBottomBar


@Composable
fun ScheduleView(
    navController: NavHostController,
    ScheduleViewModel: ScheduleViewModel = viewModel()
) {
    val selectedDay = ScheduleViewModel.selectedDay.collectAsState()
    val currentSchedules = ScheduleViewModel.currentSchedules.collectAsState()

    Scaffold(
        bottomBar = { MyBottomBar(navController)  }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {

            HeaderSection( onCalendarClick = { navController.navigate("schedules")  })


            DaySelectionRow(  ScheduleViewModel)

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                // Schedule Content
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                ) {
                    if (currentSchedules.value.isEmpty()) {
                        item {
                            Text(
                                text = "Sem horários para ${selectedDay.value}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                modifier = Modifier.padding(vertical = 16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        item (currentSchedules.value ){
                            ScheduleCard(
                                time = currentSchedules.value[0].time,
                                title = currentSchedules.value[0].title,
                                subtitle = currentSchedules.value[0].subtitle,
                                room = currentSchedules.value[0].room,
                                location = currentSchedules.value[0].location,
                                backgroundColor = Color(0xFFB2DFDB)
                            )
                        }
                        item {
                            ScheduleCard(
                                time = currentSchedules.value[1].time,
                                title = currentSchedules.value[1].title,
                                subtitle = currentSchedules.value[1].subtitle,
                                room = currentSchedules.value[1].room,
                                location = currentSchedules.value[1].location,
                                backgroundColor = Color(0xFFF5F5F5)
                            )
                        }
                    }
                }
            }


           ExportButton( ScheduleViewModel)
        }
    }
}
@Composable
fun HeaderSection(onCalendarClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "11",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Segunda",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Nov 2024",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            // Calendar Icon Button
            IconButton(onClick = onCalendarClick) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Abrir Calendário",
                    tint = Color(0xFF3AB397)
                )
            }
        }
    }
}

@Composable
fun DaySelectionRow(viewModel: ScheduleViewModel) {
    val days = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
    val dates = listOf(10, 11, 12, 13, 14, 15, 16)

    val selectedDay = viewModel.selectedDay.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        days.forEachIndexed { index, day ->
            val isSelected = selectedDay.value == day
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    viewModel.selectDay(day)
                }
            ) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) Color(0xFF008066) else Color.Gray
                )
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFF008066) else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dates[index].toString(),
                        color = if (isSelected) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun ScheduleCard(
    time: String,
    title: String,
    subtitle: String,
    room: String,
    location: String,
    backgroundColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier
                .width(60.dp)
                .padding(end = 8.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Black
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Location",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$room, $location",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ExportButton( viewModel: ScheduleViewModel) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, bottom = 100.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {viewModel.exportSchedule(context) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008066)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(0.4f)
        ) {
            Text(
                text = "Exportar",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ScheduleViewPreview() {
    ScheduleView( navController = NavHostController(LocalContext.current))
}

