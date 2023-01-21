package tasklist

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

object JsonUtil {
    private val jsonFile = File("tasklist.json")

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun writeToJson(tasks: MutableList<Task>) {
        val type = Types.newParameterizedType(MutableList::class.java, Task::class.java)
        val taskListAdapter = moshi.adapter<MutableList<Task>>(type)

        jsonFile.writeText(taskListAdapter.toJson(tasks))
    }

    fun readFromJson(): MutableList<Task> {
        var tasks = mutableListOf<Task>()
        if (jsonFile.exists()) {
            val type = Types.newParameterizedType(MutableList::class.java, Task::class.java)
            val taskListAdapter = moshi.adapter<MutableList<Task>>(type)
            val content = jsonFile.bufferedReader().use { it.readText().trimIndent() }
            tasks = taskListAdapter.fromJson(content) ?: throw Exception("Can't read!")
        }
        return tasks
    }
}