package ipca.example.pcmp.ui.news

import android.content.Context
import android.content.Intent


fun shareNews(context: Context, newsTitle: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Check out this news: $newsTitle")
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}
