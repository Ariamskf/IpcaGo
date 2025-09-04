package ipca.example.pcmp.ui.ucs

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ipca.example.pcmp.R


@Composable
fun ucDetailsView(
    context: Context,
    viewModel: UcDetailsViewModel,
    onClose: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    MaterialTheme {
        UcDetailsContent(
            onDownload = { viewModel.downloadDocument(context) },
            onShare = { viewModel.shareDocument(context) },
            onClose = onClose,
            viewModel = viewModel
        )
    }
}

@Composable
fun UcDetailsContent(
    onDownload: () -> Unit,
    onShare: () -> Unit,
    onClose: () -> Unit,
    viewModel: UcDetailsViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.ipca_pt),
                    contentDescription = "IPCA Logo",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .height(65.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Linguagens de Programação",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Curso Técnico Superior Profissional em Aplicações Móveis",
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            SectionWithDivider(title = "Código", content = "322060")
            SectionWithDivider(
                title = "Área Científica Predominante",
                content = "Programação e desenvolvimento de software"
            )
            SectionWithDivider(title = "Docente", content = "Sofia Sousa Costa")
            SectionWithDivider(title = "Idioma de Instrução", content = "Português")
            SectionWithDivider(title = "Regime", content = "S1")
            SectionWithDivider(title = "Carga Letiva", content = "50h")
            SectionWithDivider(title = "Carga Trabalho", content = "90h")
            SectionWithDivider(title = "ECTS", content = "5,0")

            Spacer(modifier = Modifier.height(16.dp))

            SectionWithHeader(
                header = "Objetivos",
                content = "O objetivo desta unidade curricular é dotar os alunos com os conhecimentos base para se iniciarem na programação. Pretende-se atingir este objetivo através da resolução de pequenos problemas da vida real."
            )

            Spacer(modifier = Modifier.height(16.dp))

            SectionWithHeader(
                header = "Resultados da Aprendizagem",
                content = "Nesta unidade curricular os alunos deverão adquirir a capacidade de implementar algoritmos simples em pequenas aplicações C."
            )

            Spacer(modifier = Modifier.height(16.dp))

            SectionWithHeader(
                header = "Conteúdos Programáticos",
                content = """
                • Introdução e conceitos básicos
                • Algoritmos e a programação
                • Tipos de instrução (Sequência, Decisão e Repetição)
                • Apresentação de ferramentas: Dev C++ (BLOODSHED) (programação em C)
            """.trimIndent()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Integrate Action Buttons
            ActionButtons(onDownload = onDownload, onShare = onShare)

            if (viewModel.statusMessage.isNotEmpty()) {
                Text(
                    text = viewModel.statusMessage,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    // Close button at the top-right corner
    IconButton(
        onClick = onClose,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = Color.Black
        )
    }
}

@Composable
fun SectionWithDivider(title: String, content: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$title:",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = content,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun SectionWithHeader(header: String, content: String) {
    Text(
        text = header,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = content,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
}

@Composable
fun ActionButtons(
    onDownload: () -> Unit,
    onShare: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Download Button
        Button(
            onClick = onDownload,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00695C))
        ) {
            Text("Download", color = Color.White)
        }

        // Share Button
        Button(
            onClick = onShare,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00695C))
        ) {
            Text("Share", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UcDetailsViewPreview() {
    ucDetailsView(LocalContext.current, UcDetailsViewModel(), {} , navController = rememberNavController())
}
