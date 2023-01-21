package tasklist

fun main() {
        val tasklist = Tasklist()

    while (true) {
        println("Input an action (add, print, edit, delete, end):")
        when (readln()) {
            "add" -> tasklist.add()
            "print" -> tasklist.printList()
            "edit" -> tasklist.edit()
            "delete" -> tasklist.delete()
            "end" -> break
            else -> println("The input action is invalid")
        }
    }
    JsonUtil.writeToJson(tasklist.getTasks())
    println("Tasklist exiting!")
}
