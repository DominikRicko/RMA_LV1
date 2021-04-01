package yamb.player

import consoleGraphics.Displayable
import data.Observer
import yamb.Yamb
import yamb.scores.Scoreboard

class YambUserPlayer(override val name: String, override val game: Yamb) : YambPlayer, Displayable{

    override var diceRolls: Int = 0

    override val scoreboard : Scoreboard = Scoreboard()

    private fun getUserInput() : Pair<String, Pair<Int,Int>>?{
        val input = (readLine()?.split(' ')) ?: return null
        val command = input[0]

        when(input.size){
            3 -> {
                val arguments = listOf(input[1], input[2]).map { it.toInt()}

                if (arguments.filter { it >= 0 }.size != arguments.size){
                    println("Error parsing command arguments")
                    return null
                }

                return Pair(command, Pair(arguments[0], arguments[1]))
            }
            2 -> return Pair(command, Pair(input[1].toInt(), -1))
            1 -> return Pair(command, Pair(-1,-1))
            else -> {
                println("Error parsing command arguments")
                return null
            }
        }
    }

    override fun processNextCommand(){

        val userInput = getUserInput() ?: return

        when (userInput.first){
            "roll "-> {
                game.rollDices()
                diceRolls--
                scoreboard.updatePredictions(Yamb.dices)
            }
            "lock" -> game.lockDice(userInput.second.first, true)
            "unlock" -> game.lockDice(userInput.second.first, false)
            "save" -> if(scoreboard.writeToScoreboard(
                    userInput.second.first,userInput.second.second))
                        diceRolls = 0

            else -> println("Unknown command")
        }

    }

    override fun getDisplayStringSet(): String {

        var outputString = "$name\n"

        outputString += scoreboard.getDisplayStringSet()

        outputString += "Commands are: sel num1 num2 - to fill in the spot on the scoreboard\n"
        outputString += "\t\tlock diceNumber - to lock a particular dice\n"
        outputString += "\t\tunlock diceNumber -to unlock a particular dice\n"
        outputString += "\t\troll - to roll all unlocked dices\n"

        outputString += Yamb.getDisplayStringSet()

        return outputString
    }

    private val observers: ArrayList<Observer<Displayable>> = arrayListOf()

    override fun subscribe(observer: Observer<Displayable>) {
        observers.add(observer)
    }

    override fun notifyObservers() {
        observers.forEach { it.update(this) }
    }

}