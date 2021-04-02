package yamb

import Game
import consoleGraphics.Displayable
import data.Observable
import data.Observer
import dice.Dice
import dice.SixSidedDice
import yamb.player.YambPlayer
import yamb.player.YambRandomAIPlayer
import yamb.player.YambUserPlayer
import yamb.scores.Scoreboard

class Yamb(private val rounds : Int) : Game, Observable<Displayable>, Displayable {

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
    private val scoreboards : HashMap<YambPlayer, Scoreboard> = hashMapOf()
    private lateinit var currentPlayer : YambPlayer

    private var diceRolls : Int = 0

    private fun doPlayerTurn() {

        diceRolls = 3
        scoreboards[currentPlayer]!!.updatePredictions(dices)

        while(diceRolls > 0){
            notifyObservers()
            currentPlayer.processNextCommand()
        }

        if(diceRolls > -1){
            notifyObservers()
            forceSave()
        }

    }

    private fun forceSave() {
        while(diceRolls > -1){
            println("You are out of rolls, you can only use save command")
            currentPlayer.processNextCommand()
        }
    }

    fun rollDices(player : YambPlayer){
        if(diceRolls <= 0) return
        diceRolls--
        dices.forEach { it.roll() }
        scoreboards[player]!!.updatePredictions(dices)
    }

    fun lockDice(whichDiceIndex : Int, lock : Boolean){
        if(whichDiceIndex < dices.size)
            dices.elementAt(whichDiceIndex).locked = lock
    }

    fun saveScore(indexX : Int, indexY : Int){
        if(scoreboards[currentPlayer]!!.writeToScoreboard(indexX,indexY))
            diceRolls = -1
    }

    private fun gameEnd(){

        val finalScoreboard = players.map { Pair(it.name ,scoreboards[it]!!.getScoreSum()) }

        println("Final scoreboard")
        println("=====================================")
        println("Name\t\t|\t\tScore\t")
        finalScoreboard.forEach{
            println("${it.first}\t|\t\t${it.second}\t")
        }

    }

    override fun start(){

        players.forEach {
            scoreboards[it] = Scoreboard()
        }

        for (i in 0 until rounds step 1){
            players.forEach { player ->
                dices.forEach { dice ->
                    dice.locked = false
                    dice.roll()
                }
                currentPlayer = player
                doPlayerTurn()
            }
        }

        gameEnd()
    }

    override fun addPlayer(playerName: String, playerType: Game.PlayerType) {

        players.add(when(playerType){
            Game.PlayerType.USER -> YambUserPlayer(playerName, this)
            Game.PlayerType.RANDOM_AI -> YambRandomAIPlayer(this)
        })

    }

    private val subscribers : ArrayList<Observer<Displayable>> = arrayListOf()

    override fun subscribe(observer: Observer<Displayable>) {
        subscribers.add(observer)
    }

    override fun notifyObservers() {
        subscribers.forEach { it.update(this) }
    }

    override fun getDisplayStringSet(): String {
        var outputString = "$currentPlayer\t\t Rolls: $diceRolls\n"

        outputString += scoreboards[currentPlayer]!!.getDisplayStringSet()

        outputString += "Commands are: save num1 num2 - to fill in the spot on the scoreboard\n"
        outputString += "\t\tlock diceNumber - to lock a particular dice\n"
        outputString += "\t\tunlock diceNumber -to unlock a particular dice\n"
        outputString += "\t\troll - to roll all unlocked dices\n"

        outputString += Yamb.getDisplayStringSet()

        return outputString
    }

}