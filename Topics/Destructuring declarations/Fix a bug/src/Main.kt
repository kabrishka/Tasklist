data class Article(val name: String, val pages: Int, val author: String)

fun getArticleByName(articles: MutableList<Article>, name: String): Article? {
    var index = 0
    articles.forEach { (title, _, _) -> if (title == name) return articles[index] else index++}
    
    return null
}
