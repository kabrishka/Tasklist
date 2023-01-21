package tasklist

import kotlinx.datetime.*
import java.time.LocalTime

val priorities = listOf("C","H","N","L")

class Task(var id: Int,
           var priority: String,
           var date: String,
           var time: String,
           var content:MutableList<String> ) {

    var dueTag = setDueTag()

    companion object {
        fun setPriority(): String {
            println("Input the task priority (C, H, N, L):")
            return readln().uppercase().let { if (it in priorities) it else setPriority() }
        }

        fun setDate(): String {
            return try {
                println("Input the date (yyyy-mm-dd):")
                readln().split("-").map { it.toInt() }.let { LocalDate(
                    it[0],
                    it[1],
                    it[2]
                ).toString() }
            } catch (e: Exception) {
                println("The input date is invalid").run { setDate() }
            }
        }

        fun setTime(): String {
            val time = println("Input the time (hh:mm):").run { readln() }
            return try {
                time.split(":").map { it.toInt() }.let { LocalTime.of(it[0], it[1]) }.toString()
            } catch (e: Exception) {
                println("The input time is invalid").run { setTime() }
            }
        }

        fun setContent(): MutableList<String> {
            val task = mutableListOf<String>()
            println("Input a new task (enter a blank line to end):")
            while (true) {
                val str = readln().trim()
                if(str.isBlank()) {
                    if (task.isEmpty()) {
                        println("The task is blank")
                        break
                    }
                    break
                }
                task.add(str)
            }
            return task
        }
    }

    private fun setDueTag(): String {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+2")).date
        val numberOfDays = currentDate.daysUntil(date.toLocalDate())
        return if (numberOfDays == 0) "T" else if (numberOfDays > 0) "I" else "O"
    }

    fun editTask() {
        println("Input a field to edit (priority, date, time, task):")
        when(readln()) {
            "priority" -> priority = setPriority()
            "date" -> date = setDate()
            "time" -> time = setTime()
            "task" -> content = setContent()
            else -> println("Invalid field").run { editTask() }
        }
    }
}