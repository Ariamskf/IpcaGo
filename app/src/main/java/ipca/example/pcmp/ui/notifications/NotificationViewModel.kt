package ipca.example.pcmp.ui.notifications

import androidx.lifecycle.ViewModel
import ipca.example.pcmp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Notification(
    val title: String,
    val description: String,
    val date: String,
    val isUnread: Boolean,
    val categoryIcon: Int,
    val route: String? = null
)

open class NotificationViewModel : ViewModel() {
    private val _notifications = MutableStateFlow(
        listOf(
            Notification(
                title = "Prazo para Preenchimento do Inquérito de Disponibilidade Horária.",
                description = "Complete o inquérito antes do prazo final.",
                date = "Sexta-feira, 13 de junho às 12h00",
                isUnread = true,
                categoryIcon = R.drawable.notification2,
                route = "inquery"
            ),
            Notification(
                title = "Semana da Inovação no Ensino!",
                description = "Participe de workshops e palestras com especialistas na área de educação e inova...",
                date = "Segunda-feira, 2 de junho às 09h00",
                isUnread = true,
                categoryIcon = R.drawable.notification1
            ),
            Notification(
                title = "Novo Comunicado: Reunião Geral dos Docentes",
                description = "Data: 23/05/2025 às 14h00.\nLocal: Auditório Principal.",
                date = "Quarta-feira, 15 de maio às 14h00",
                isUnread = false,
                categoryIcon = R.drawable.notification2
            ),
            Notification(
                title = "O cronograma foi atualizado para a disciplina Programação Web.",
                description = "O horário mudou para: Segunda-feira, 10h00 - 12h00, sala 8!",
                date = "Quarta-feira, 7 de maio às 10h00",
                isUnread = false,
                categoryIcon = R.drawable.notification2
            )
        )
    )

    open val notifications: StateFlow<List<Notification>> = _notifications
}
