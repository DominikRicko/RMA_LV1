package yamb

import Game
import dice.Dice
import dice.SixSidedDice
import yamb.player.YambPlayer
import yamb.player.YambRandomAIPlayer
import yamb.player.YambUserPlayer

class Yamb(private val rounds : Int) : Game {

    private val players : ArrayList<YambPlayer> = arrayListOf()
    private val dices : Collection<Dice> = listOf(
        SixSidedDice(),
        SixSidedDice(),
        SixSidedDice(),
        SixSidedDice(),
        SixSidedDice(),
        SixSidedDice()
    )

    private fun gameEnd(){

        val finalScoreboard = players.map { it -> Pair(it.name ,it.getTotalScore()) }

        println("Final scoreboard")
        println("=====================================")
        println("Name\t\t|\t\tScore\t")
        finalScoreboard.forEach{
            println("${it.first}\t|\t\t${it.second}\t")
        }

    }

    private fun gameLoop(){

        for (i in 0 until rounds step 1){
            players.forEach { it.doPlayerTurn() }
        }

        gameEnd()

    }

    override fun start(){
        players.forEach{ it.giveDices(dices) }
        gameLoop()
    }

    override fun addPlayer(playerName: String) {
        players.add(YambUserPlayer(playerName))
        //players.add(YambRandomAIPlayer())
    }

    override fun exit() {
        TODO("Not yet implemented")
    }

}