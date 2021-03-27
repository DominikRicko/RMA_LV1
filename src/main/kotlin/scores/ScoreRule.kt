package scores

import dice.Dice

interface ScoreRule {

    fun getDiceScore(dices : Collection<Dice>) : Int

}