package scores

import dice.Dice

object ScoreRuleYamb : ScoreRule {

    override fun getDiceScore(dices: Collection<Dice>): Int {

        val groupedDices = dices
            .groupBy { it.side }
            .map{ it-> Pair(it.key, it.value.size)}

        val mostDiceSide = groupedDices.maxByOrNull { it.second }!!

        return if(mostDiceSide.second >= 5)
            mostDiceSide.first * 5 + 50
        else
            0

    }

}