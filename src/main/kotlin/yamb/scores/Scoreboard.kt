package yamb.scores

import data.Matrix
import dice.Dice

class Scoreboard{

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

    val data : Matrix<Score> = Matrix(columnHeaders.size, rowHeaders.size) { Score(Score.NO_SCORE_VALUE) }

    fun getAvailableCellIndices() : Collection<Pair<Int,Int>>{
        val availableCells : ArrayList<Pair<Int,Int>> = arrayListOf()

        columnHeaders.forEachIndexed{ index, header ->
            val indices = header.second.getAvailableIndices(data.getColumn(index))

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
        data.forEach { if(it.value != Score.NO_SCORE_VALUE) sum += it.value }
        return sum
    }

    override fun toString(): String {
        var outputString = ""
        columnHeaders.forEach { outputString += "\t " + it.first + " | " }
        for (i in 0..data.sizeX step 1){
            val row = data.getRow(i)
            outputString += "\t " + rowHeaders[i].first + " | "
            row.forEach{ outputString += "\t $it | "  }
            outputString += "\n"
        }
        return outputString
    }

}