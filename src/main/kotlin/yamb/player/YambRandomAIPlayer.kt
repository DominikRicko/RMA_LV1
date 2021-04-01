package yamb.player

import consoleGraphics.Displayable
import data.Observer
import yamb.Yamb
import yamb.scores.Scoreboard

class YambRandomAIPlayer(override val name: String, override val game: Yamb) : YambPlayer, Displayable {

    companion object{

        private var counter = 0

        operator fun invoke(game : Yamb): YambRandomAIPlayer = YambRandomAIPlayer("AI $counter++", game)

    }

    override var diceRolls: Int = 0

    override val scoreboard: Scoreboard = Scoreboard()
    override fun processNextCommand() {

        val command = (0..3).random()

        val argument1 = (0 until Scoreboard.rowHeaders.size).random()
        val argument2 = (0 until Scoreboard.columnHeaders.size).random()

        when (command){
            0-> {
                game.rollDices()
                diceRolls--
                scoreboard.updatePredictions(Yamb.dices)
            }
            1 -> game.lockDice(argument1, true)
            2 -> game.lockDice(argument1, false)
            3 -> if(scoreboard.writeToScoreboard(argument1,argument2))
                diceRolls = 0
        }
    }

    override fun getDisplayStringSet(): String {

        var outputString = "$name\n"
        outputString += scoreboard.getDisplayStringSet()
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