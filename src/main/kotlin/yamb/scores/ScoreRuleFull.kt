package yamb.scores

import dice.Dice

object ScoreRuleFull : ScoreRule {

    override fun getDiceScore(dices: Collection<Dice>) : Score{

        val countedSides = dices
            .groupBy{it.side}.toSortedMap()
            .map{ it -> Pair(it.key,it.value.size)}
            .toTypedArray()

        var higherTripletOrMore = 0
        var lowerPairOrTriplet = 0

        countedSides.reversed().forEach{
            val count = it.second
            if(higherTripletOrMore == 0 && count >= 3)
                higherTripletOrMore = it.first
            else if(count >= 2){
                lowerPairOrTriplet = it.first
                return@forEach
            }

        }

        var score = 0

        if(higherTripletOrMore != 0 && lowerPairOrTriplet != 0){
            score += higherTripletOrMore * 3
            score += lowerPairOrTriplet * 2
            score += 30
        }

        return Score(score)

    }
}