package yamb.scores

import dice.Dice

object ScoreRulePoker : ScoreRule {

    override fun getDiceScore(dices: Collection<Dice>): Score {

        val groupedDices = dices
            .groupBy { it.side }
            .map { it -> Pair(it.key, it.value.size) }

        val mostDiceSide = groupedDices.maxByOrNull { it.second }!!

        val sortedGroupedDices = groupedDices.sortedBy { it.first }.toTypedArray()
        val highestDice = sortedGroupedDices.last()

        return Score(
            if (mostDiceSide.second >= 5)
                mostDiceSide.first * 4 + highestDice.first + 40
            else if (mostDiceSide.second == 4 && highestDice.first != mostDiceSide.first)
                mostDiceSide.first * 4 + highestDice.first + 40
            else if (mostDiceSide.second == 4 && dices.size > 4) {
                val secondHighestDice = sortedGroupedDices[sortedGroupedDices.size - 2]
                mostDiceSide.first * 4 + secondHighestDice.first + 40
            } else if (mostDiceSide.second == 4)
                mostDiceSide.first * 4 + 40
            else
                0
        )
    }
}