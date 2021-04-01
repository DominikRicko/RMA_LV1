package yamb.player

import consoleGraphics.Displayable
import data.Observable
import yamb.Yamb
import yamb.scores.Scoreboard

interface YambPlayer : Observable<Displayable> {
    var diceRolls : Int
    val name : String
    val scoreboard : Scoreboard
    val game : Yamb

    fun processNextCommand()

    fun forceSave()

    fun getTotalScore() : Int{
        return scoreboard.getScoreSum()
    }

    fun doPlayerTurn() {
        diceRolls = 3
        scoreboard.updatePredictions(Yamb.dices)

        while(diceRolls > 0){
            notifyObservers()
            processNextCommand()
        }

        if(diceRolls != -1){
            notifyObservers()
            forceSave()
        }

    }

}