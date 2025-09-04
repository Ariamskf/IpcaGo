package ipca.example.pcmp.ui.homepage


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.example.pcmp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Course(
    val title: String,
    val location: String
)

data class News(
    val title: String,
    val imageResId: Int
)


class HomepageViewModel : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news

    private val _schedule = MutableStateFlow("Introdução à Computação Gráfica\nGuimarães (AM)")
    val schedule: StateFlow<String> = _schedule

    init {

        loadCourses()
        loadNews()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            _courses.value = listOf(
                Course("Aplicações Móveis", "Guimarães"),
                Course("Desenvolvimento Web e Multimédia", "Vila Verde")
            )
        }
    }

    private fun loadNews() {
        viewModelScope.launch {
            _news.value = listOf(
                News("Notícia 1", R.drawable.image002),
                News("Notícia 2", R.drawable.image002)
            )
        }
    }
}



