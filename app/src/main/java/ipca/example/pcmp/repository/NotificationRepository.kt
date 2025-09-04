package ipca.example.pcmp.repository

import ipca.example.pcmp.R
import ipca.example.pcmp.models.Notification

class NotificationRepository {
    fun getNotification(): List<Notification> {
        return listOf(
            Notification(
                id = 1.toString(),
                title = "Prazo para Preenchimento do Inquérito",
                description = "Complete o inquérito antes do prazo final.",
                date = "Sexta-feira, 13 de junho às 12h00",
                isRead = false,
                categoryIcon = R.drawable.notification1,
                route = "inquiry"
            ),
            Notification(
                id = 2.toString(),
                title = "Semana da Inovação no Ensino!",
                description = "Participe de workshops e palestras na área de educação.",
                date = "Segunda-feira, 2 de junho às 09h00",
                isRead = false,
                categoryIcon = R.drawable.notification1

            ),
            Notification(
                id = 3.toString(),
                title = "Novo Comunicado: Reunião Geral dos Docentes",
                description = "Data: 23/05/2025 às 14h00. Local: Auditório Principal.",
                date = "Quarta-feira, 7 de maio às 14h00",
                isRead = true,
                categoryIcon = R.drawable.notification2

            ),
            Notification(
                id = 4.toString(),
                title = "O cronograma foi atualizado para a disciplina Programação Web.",
                description = "O horário mudou para: Segunda-feira, 10h00 - 12h00, sala 8!",
                date = "Segunda-feira, 7 de maio às 10h00",
                isRead = true,
                categoryIcon = R.drawable.notification2
            )
        )
    }
}