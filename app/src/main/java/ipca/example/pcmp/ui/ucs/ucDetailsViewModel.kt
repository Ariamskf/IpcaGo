package ipca.example.pcmp.ui.ucs

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.FileOutputStream


class UcDetailsViewModel : ViewModel() {
    var statusMessage: String = ""
        private set

    fun downloadDocument(context: Context) {
try {
        val content = """
                Linguagens de Programação
                Curso Técnico Superior Profissional em Aplicações Móveis
                
                Código: 322060
                Área Científica Predominante: Programação e desenvolvimento de software
                Docente: Sofia Sousa Costa
                Idioma de Instrução: Português
                Regime: S1
                Carga Letiva: 50h
                Carga Trabalho: 90h
                ECTS: 5,0
                
                Objetivos:
                O objetivo desta unidade curricular é dotar os alunos com os conhecimentos base para se iniciarem na programação. Pretende-se atingir este objetivo através da resolução de pequenos problemas da vida real.
                
                Resultados da Aprendizagem:
                Nesta unidade curricular os alunos deverão adquirir a capacidade de implementar algoritmos simples em pequenas aplicações C.
                
                Conteúdos Programáticos:
                • Introdução e conceitos básicos
                • Algoritmos e a programação
                • Tipos de instrução (Sequência, Decisão e Repetição)
                • Apresentação de ferramentas: Dev C++ (BLOODSHED) (programação em C)
            """.trimIndent()
    val fileName = "UC_Details.txt"
    val file = File(context.getExternalFilesDir(null), fileName)

    // Write content to file
    FileOutputStream(file).use { outputStream ->
        outputStream.write(content.toByteArray())
    }

    // Prepare download intent
    val downloadIntent = Intent(Intent.ACTION_VIEW).apply {
        val fileUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        setDataAndType(fileUri, "text/plain")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    // Start download activity
    context.startActivity(Intent.createChooser(downloadIntent, "Download UC Details Via"))
} catch (e: Exception) {
    e.printStackTrace()
    Toast.makeText(context, "Error during download: ${e.message}", Toast.LENGTH_LONG).show()
}
    }
        fun shareDocument(context: Context) {
            try {
                val fileName = "document.txt"
                val file = File(context.filesDir, fileName)
                if (!file.exists()) {
                    downloadDocument(context)
                }
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, file.readText())
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share Document"))
            } catch (e: Exception) {
                statusMessage = "Error during sharing: ${e.message}"
            }
        }
    }




