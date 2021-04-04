package yamb.player

import yamb.Yamb

class YambUserPlayer(override val name: String, override val game: Yamb) : YambPlayer {

    private fun getUserInput(): Pair<String, Pair<Int, Int>>? {
        val input = (readLine()?.split(' ')) ?: return null
        val command = input[0]

        when (input.size) {
            3 -> {
                val arguments = listOf(input[1], input[2]).map { it.toInt() }

                if (arguments.filter { it >= 0 }.size != arguments.size) {
                    println("Error parsing command arguments")
                    return null
                }

                return Pair(command, Pair(arguments[0], arguments[1]))
            }
            2 -> return Pair(command, Pair(input[1].toInt(), -1))
            1 -> return Pair(command, Pair(-1, -1))
            else -> {
                println("Error parsing command arguments")
                return null
            }
        }
    }

    override fun processNextCommand() {

        val userInput = getUserInput() ?: return

        when (userInput.first) {
            "roll" -> game.rollDices(this)
            "lock" -> game.lockDice(userInput.second.first, true)
            "unlock" -> game.lockDice(userInput.second.first, false)
            "save" -> game.saveScore(userInput.second.first, userInput.second.second)
            else -> println("Unknown command")
        }

    }

    override fun toString(): String {

        return name

    }

}