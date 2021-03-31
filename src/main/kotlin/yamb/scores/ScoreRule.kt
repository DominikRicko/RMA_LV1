package yamb.scores

import yamb.dice.Dice

interface ScoreRule {

    fun getDiceScore(dices : Collection<Dice>) : Score

}