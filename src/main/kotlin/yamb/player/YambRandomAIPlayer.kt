package yamb.player

import yamb.Yamb
import yamb.scores.Scoreboard

class YambRandomAIPlayer(override val name: String, override val game: Yamb) : YambPlayer {

    companion object {

        private var counter = 0

        operator fun invoke(game: Yamb): YambRandomAIPlayer = YambRandomAIPlayer("AI ${counter++}", game)

    }

    override fun processNextCommand() {

        val command = (0..3).random()

        val argument1 = (0 until Scoreboard.rowHeaders.size).random()
        val argument2 = (0 until Scoreboard.columnHeaders.size).random()

        when (command) {
            0 -> game.rollDices(this)
            1 -> game.lockDice(argument1, true)
            2 -> game.lockDice(argument1, false)
            3 -> game.saveScore(argument1, argument2)
        }
    }

    override fun toString(): String {

        return name

    }

}