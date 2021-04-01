package yamb.player

import dice.Dice

interface YambPlayer {
    val name : String
    fun doPlayerTurn()
    fun giveDices(dices : Collection<Dice>)
    fun getTotalScore() : Int
}