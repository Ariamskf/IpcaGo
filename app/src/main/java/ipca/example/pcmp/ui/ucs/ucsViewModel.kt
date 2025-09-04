package ipca.example.pcmp.ui.ucs

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ucs(
    val name: String,
    val progress: Int,
    val semester: String,
    val  matches: Unit

)



class ucsViewModel : ViewModel() {
    private val _units = MutableStateFlow(
        listOf(
            ucs("Linguagens de Programação", 75, "S1", matches = Unit),
            ucs("Introdução à Computação Gráfica", 50, "S1", matches = Unit),
            ucs("Computação Móvel I", 0, "S2", matches = Unit),
            ucs("Projeto de Iniciação à Programação", 40, "S1", matches = Unit),
            ucs("Projeto de Computação Móvel Avançada", 25, "S1", matches = Unit)
        )
    )

    val units: StateFlow<List<ucs>> get() = _units
}
