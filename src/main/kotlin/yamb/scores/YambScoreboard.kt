package yamb.scores

import data.Matrix
import yamb.dice.Dice

class YambScoreboard : ScoreBoard {

    companion object{
        val rowHeaders : ArrayList<Pair<String,ScoreRule>> = ArrayList()
        val columnHeaders : ArrayList<Pair<String,ScoreColumnRule>> = ArrayList()

        init{
            rowHeaders.add(Pair("Full",ScoreRuleFull))
            rowHeaders.add(Pair("Poker",ScoreRulePoker))
            rowHeaders.add(Pair("Straight",ScoreRuleStraight))
            rowHeaders.add(Pair("Yamb",ScoreRuleYamb))

            columnHeaders.add(Pair("\\/",ScoreColumnRuleTopDown))
            columnHeaders.add(Pair("/\\",ScoreColumnRuleBottomUp))
            columnHeaders.add(Pair("\\/\\",ScoreColumnRuleAny))
        }

    }

    val scoreboard : Matrix<Score> = Matrix(columnHeaders.size, rowHeaders.size) { Score(Score.NO_SCORE_VALUE) }

    fun getAvailableCellIndices() : Collection<Pair<Int,Int>>{
        val availableCells : ArrayList<Pair<Int,Int>> = arrayListOf()

        columnHeaders.forEachIndexed{ index, header ->
            val indices = header.second.getAvailableIndices(scoreboard.getColumn(index))

            indices.forEach {
                availableCells.add(Pair(index, it))
            }

        }

        return availableCells

    }

    fun getScorePredictions(dices : Collection<Dice>) : Collection<Score>{

        val scores : ArrayList<Score> = arrayListOf()

        rowHeaders.forEach{scores.add(it.second.getDiceScore(dices))}

        return scores
    }

    fun getScoreSum() : Int{
        var sum = 0
        scoreboard.forEach { if(it.value != Score.NO_SCORE_VALUE) sum += it.value }
        return sum
    }

}