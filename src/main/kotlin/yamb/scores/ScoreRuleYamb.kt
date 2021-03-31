package yamb.scores

import yamb.dice.Dice

object ScoreRuleYamb : ScoreRule {

    override fun getDiceScore(dices: Collection<Dice>): Score {

        val groupedDices = dices
            .groupBy { it.side }
            .map{ it-> Pair(it.key, it.value.size)}

        val mostDiceSide = groupedDices.maxByOrNull { it.second }!!

        return Score(
            if(mostDiceSide.second >= 5)
                mostDiceSide.first * 5 + 50
            else
                0
        )
    }

}