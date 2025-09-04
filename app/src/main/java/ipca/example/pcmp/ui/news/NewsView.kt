package ipca.example.pcmp.ui.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ipca.example.pcmp.R

import android.content.Context
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.mutableStateListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsView(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onCommentClick: () -> Unit,
    onLikeClick: () -> Unit,
    onShareClick: (Context, String) -> Unit
) {
    var likesCount by remember { mutableStateOf(0) }
    var isLiked by remember { mutableStateOf(false) }
    var comments = remember { mutableStateListOf<String>() }
    var commentText by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onBookmarkClick() }) {
                        Icon(Icons.Outlined.Star, contentDescription = "Save" , tint = Color(0xFF3AB397))
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Image
            Image(
                painter = painterResource(id = R.drawable.noticia),
                contentDescription = "Conference Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title Section
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Conferência | Design Funcional: o Desafiador da Mudança",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "8 de Novembro 2024",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Comments
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onCommentClick() }) {
                        Icon(Icons.Default.Email, contentDescription = "Comment", tint = Color(0xFF3AB397))
                    }
                    Text(text = "${comments.size} comentários")
                }


                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        isLiked = !isLiked
                        likesCount = if (isLiked) likesCount + 1 else likesCount - 1
                    }) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) Color(0xFF3AB397) else Color(0xFF3AB397)
                        )
                    }
                    Text(text = "$likesCount Gostos")
                }

                // Share
                IconButton(
                    onClick = { onShareClick(context, "Conferência | Design Funcional: o Desafiador da Mudança") }
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content Section
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "O evento, a decorrer no Auditório António Tavares, no Campus do IPCA, em Barcelos, conta com um painel de luxo! A abertura estará a cargo de António Carlos Rodrigues, CEO do Grupo Casais. Segue-se sessão de palestras e mesa-redonda com Filipe Mesquita,  designer gráfico na “This is Pacifica”, Nuno Baltazar, designer de moda na “Nuno Baltazar Studio”, Luís Jorge, engenheiro civil na “TISEM” e Gabriel Tan, designer industrial na “Gabriel Tan Studio”. A sessão será moderada por Dina Oliveira, Innovation & Design Thinking, e Jorge Brandão Pereira, Diretor da ESD, fará o encerramento da sessão.\n" +
                            "\n" +
                            "A entrada é gratuita, sujeita a inscrição através do link:\n" +
                            " https://mailchi.mp/casais.pt/ljusa7wx3t",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Comments Section
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Comentários",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF3AB397)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Comments List
                comments.forEach { comment ->
                    Text(
                        text = comment,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Add Comment Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        placeholder = { Text(text = "Adicione um comentário...") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White
                        )
                    )
                    IconButton(
                        onClick = {
                            if (commentText.isNotBlank()) {
                                comments.add(commentText)
                                commentText = ""
                            }
                        }
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send Comment", tint = Color(0xFF3AB397))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
