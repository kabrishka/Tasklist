package tasklist

class Tasklist {
    private var tasks = JsonUtil.readFromJson()

    private val priorityColor = mutableMapOf("C" to "\u001B[101m \u001B[0m",
        "H" to "\u001B[103m \u001B[0m",
        "N" to "\u001B[102m \u001B[0m",
        "L" to "\u001B[104m \u001B[0m")
    private val dueTagColor = mutableMapOf("I" to "\u001B[102m \u001B[0m",
        "T" to "\u001B[103m \u001B[0m",
        "O" to "\u001B[101m \u001B[0m")

    fun getTasks(): MutableList<Task> = tasks

    fun add() {
        val priority = Task.setPriority()
        val date = Task.setDate()
        val time = Task.setTime()
        val content = Task.setContent()
        val task = Task(tasks.size + 1, priority, date, time, content)

        if (task.content.isNotEmpty()) tasks.add(task)
    }

    fun edit() {
        if (tasks.isEmpty()) {
            println("No tasks have been input")
        } else {
            printList()
            val last = tasks.size

            var number: Int
            while (true) {
                println("Input the task number (1-$last):")
                try {
                    number = readln().toInt()
                    if (number in 1 .. last) {
                        break
                    }
                    println("Invalid task number")
                } catch (e: Exception) {
                    println("Invalid task number")
                    continue
                }
            }

            tasks[number - 1].editTask()
            println("The task is changed")
        }
    }

    fun delete() {
        if (tasks.isEmpty()) {
            println("No tasks have been input")
        } else {
            printList()
            val last = tasks.size

            var number: Int
            while (true) {
                println("Input the task number (1-$last):")
                try {
                    number = readln().toInt()
                    if (number in 1 .. last) {
                        break
                    }
                    println("Invalid task number")
                } catch (e: Exception) {
                    println("Invalid task number")
                    continue
                }

            }

            tasks.removeAt(number - 1)
            tasks.mapIndexed { index, task -> task.id = index + 1 }
            println("The task is deleted")
        }
    }

    fun printList() {
        if (tasks.isEmpty()) println("No tasks have been input")
        else {
            createTable(tasks)
        }
    }

    private fun createTable(tasks: MutableList<Task>) {
        fun MutableList<String>.splitText(): MutableList<String> {
            val remains = mutableListOf<String>()//broken lines of 44 characters each
            for (str in this) {
                var startIndex = 0
                var endIndex = 44
                if (str.length > 43) {
                    while (true) {
                        val substring = str.substring(startIndex, endIndex)
                        if (substring.isBlank()) break
                        if (substring.length > 43) {
                            remains.add(substring)
                            startIndex = endIndex
                            endIndex += 44
                            if (endIndex > str.length - 1) endIndex = str.length
                        } else {
                            remains.add(substring)
                            break
                        }
                    }
                } else {
                    remains.add(str)
                }

            }
            return remains
        }

        val separator = "+----+------------+-------+---+---+--------------------------------------------+"
        val title = "| N  |    Date    | Time  | P | D |                   Task                     |"
        val pattern1 = "| %-2d | %10s | %5s | %s | %s |%-44s|"
        val pattern2 = "|    |            |       |   |   |%-44s|"
        println(separator)
        println(title)
        println(separator)
        for (i in tasks.indices) {
            val priority = priorityColor.getValue(tasks[i].priority)
            val dueTag = dueTagColor.getValue(tasks[i].dueTag)
            val splitTask = tasks[i].content.splitText()
            for (index in splitTask.indices) {
                if (index == 0) {
                    println(String.format(pattern1,tasks[i].id, tasks[i].date, tasks[i].time, priority, dueTag, splitTask[index]))
                } else {
                    println(String.format(pattern2, splitTask[index]))
                }
            }
            println(separator)
        }
    }

}