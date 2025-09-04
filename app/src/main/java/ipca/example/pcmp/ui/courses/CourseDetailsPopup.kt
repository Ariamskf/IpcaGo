package ipca.example.pcmp.ui.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun CourseDetailsPopup(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Aplicações Móveis",
                        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF008066)
                    )
                    IconButton(onClick = { /* Handle notification */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notification Icon",
                            tint = Color(0xFF008066)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tags Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TESP",
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color(0xFF008066), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Info Section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar Icon",
                        tint = Color(0xFF008066)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Laboral", style = MaterialTheme.typography.h4)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Icon",
                        tint = Color(0xFF008066)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Guimarães & Vila-Verde", style = MaterialTheme.typography.body2)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Description Section
                Text(
                    text = "Descrição:",
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = "O técnico de Aplicações Móveis é um profissional com competências técnicas que, de forma autónoma ou integrado numa equipa, planeia, analisa, desenha e desenvolve aplicações para dispositivos móveis.",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Activities Section
                Text(
                    text = "Atividades Principais:",
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = """
                        • Analisar problemas reais de complexidade variável;
                        • Planear e desenvolvimento de aplicações móveis;
                        • Desenhar arquiteturas de sistemas móveis;
                        • Desenvolver/Programar aplicações para dispositivos móveis;
                        • Aplicar tecnologias de apoio à criação de aplicações móveis;
                        • Integrar componentes multimédia.
                    """.trimIndent(),
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text("Fechar", color = Color(0xFF008066))
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
fun CourseDetailsPopupPreview() {
    CourseDetailsPopup(onDismissRequest = {})
}