package yamb

import Game
import consoleGraphics.ConsolePrinter
import consoleGraphics.Displayable
import dice.Dice
import dice.SixSidedDice
import yamb.player.YambPlayer
import yamb.player.YambRandomAIPlayer
import yamb.player.YambUserPlayer

class Yamb(private val rounds : Int) : Game{

    companion object : Displayable{
        val dices : Collection<Dice> = listOf(
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice()
        )

        override fun getDisplayStringSet(): String {

            var outputString = ""

            dices.forEachIndexed{ index, dice ->
                outputString += "Dice $index: ${if (dice.locked) "locked" else "unlocked"} side: ${dice.side}\n"
            }

            return outputString
        }

    }

    private val players : ArrayList<YambPlayer> = arrayListOf()

    fun rollDices(){
        dices.forEach { it.roll() }
    }

    fun lockDice(whichDiceIndex : Int, lock : Boolean){
        if(whichDiceIndex < dices.size)
            dices.elementAt(whichDiceIndex).locked = lock
    }


    private fun gameEnd(){

        val finalScoreboard = players.map { Pair(it.name ,it.getTotalScore()) }

        println("Final scoreboard")
        println("=====================================")
        println("Name\t\t|\t\tScore\t")
        finalScoreboard.forEach{
            println("${it.first}\t|\t\t${it.second}\t")
        }

    }

    override fun start(){

        for (i in 0 until rounds step 1){
            players.forEach { player ->
                dices.forEach { dice ->
                    dice.locked = false
                    dice.roll()
                }
                player.doPlayerTurn()
            }
        }

        gameEnd()
    }

    override fun addPlayer(playerName: String, playerType: Game.PlayerType) {

        val newPlayer = when(playerType){
            Game.PlayerType.USER -> YambUserPlayer(playerName, this)
            Game.PlayerType.RANDOM_AI -> YambRandomAIPlayer(this)
        }

        newPlayer.subscribe(ConsolePrinter)
        players.add(newPlayer)

    }

}