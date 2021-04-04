package yamb.scores

import dice.Dice

interface ScoreRule {

    fun getDiceScore(dices: Collection<Dice>): Score

}