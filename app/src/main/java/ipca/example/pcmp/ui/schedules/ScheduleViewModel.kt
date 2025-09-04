package ipca.example.pcmp.ui.schedules


import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class Schedule(
    val time: String,
    val title: String,
    val subtitle: String,
    val room: String,
    val location: String,
    val backgroundColor: Int = (0xFFF5F5F5.toInt())
)

class ScheduleViewModel : ViewModel() {

    // Mapping of schedules based on days
    private val daySchedules: Map<String, List<Schedule>> = mapOf(
        "Seg" to listOf(
            Schedule("14h00", "Aplicações Móveis", "Introdução à Computação Gráfica", "Sala 5", "Guimarães"),
            Schedule("16h10", "Aplicações Móveis", "Introdução à Computação Gráfica", "Sala 5", "Guimarães")
        ),
        "Qua" to listOf(
            Schedule("10h00", "Desenvolvimento Web Multimédia", "Programação Web I", "Sala 1", "Vila Verde"),
            Schedule("12h00", "Desenvolvimento Web Multimédia", "Programação Web 1", "Sala 1", "Vila Verde")
        ),
        "Sex" to listOf(
            Schedule("9h00", "Aplicações Móveis", "Introdução à Computação Gráfica", "Sala 2", "Vila Verde"),
            Schedule("11h00", "Aplicações Móveis", "Introdução à Computação Gráfica", "Sala 2", "Vila Verde")
        )
        // Add more days and schedules as needed
    )

    // State for the selected day
    private val _selectedDay = MutableStateFlow("Seg") // Default day is "D"
    val selectedDay: StateFlow<String> = _selectedDay

    // State for the schedules of the selected day
    private val _currentSchedules = MutableStateFlow(daySchedules["Seg"] ?: emptyList())
    val currentSchedules: StateFlow<List<Schedule>> = _currentSchedules

    // Function to select a day and update schedules
    fun selectDay(day: String) {
        if (!daySchedules.containsKey(day)) {
            Log.w("ScheduleViewModel", "Day '$day' not found in schedule mapping.")
        }

        _selectedDay.value = day
        _currentSchedules.value = daySchedules[day] ?: emptyList()
    }
    // Function to export the schedule as a CSV file
    fun exportSchedule(context: Context) {
        // Dynamically build the schedule for the selected day
        val selectedSchedules = _currentSchedules.value
        if (selectedSchedules.isEmpty()) {
            Toast.makeText(context, "Sem horários para exportar!", Toast.LENGTH_SHORT).show()
            return
        }

        val scheduleText = buildString {
            append("Horário para ${_selectedDay.value}:\n\n")
            selectedSchedules.forEach { schedule ->
                append("- ${schedule.time}: ${schedule.title} (${schedule.room}, ${schedule.location})\n")
            }
        }

        // Create a sharing intent
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, scheduleText)
            putExtra(Intent.EXTRA_SUBJECT, "Exportar do Horário")
        }

        // Start the share activity
        context.startActivity(Intent.createChooser(shareIntent, "Exportar Horário Via"))
    }
}
