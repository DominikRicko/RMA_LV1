package yamb.scores

import yamb.dice.Dice

object ScoreRuleStraight : ScoreRule {

    override fun getDiceScore(dices: Collection<Dice>): Score {

        val countedSides = dices
            .groupBy{it.side}.toSortedMap()
            .map{ it -> it.key }.reversed()
            .toTypedArray()

        var largeStraightChecker = 6
        var smallStraightChecker = 5

        countedSides.forEach {
            if(it == largeStraightChecker){
                largeStraightChecker--
            }

            if(it == smallStraightChecker){
                smallStraightChecker--
            }

            if(largeStraightChecker == 1){
                return Score(40)
            }

            if(smallStraightChecker == 0){
                return Score(30)
            }
        }

        return Score(0)

    }

}