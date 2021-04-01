package yamb.scores

import consoleGraphics.Displayable
import data.Matrix
import dice.Dice

class Scoreboard : Displayable {

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
            columnHeaders.add(Pair("-",ScoreColumnRuleAny))
        }

    }

    private val data : Matrix<Score> = Matrix(columnHeaders.size, rowHeaders.size) { Score(Score.NO_SCORE_VALUE) }
    private lateinit var validCells : Collection<Pair<Int,Int>>
    private lateinit var scores : Collection<Score>

    private fun getAvailableCellIndices() : Collection<Pair<Int,Int>>{
        val availableCells : ArrayList<Pair<Int,Int>> = arrayListOf()

        columnHeaders.forEachIndexed{ index, header ->
            val indices = header.second.getAvailableIndices(data.getColumn(index))

            indices.forEach {
                availableCells.add(Pair(index, it))
            }

        }

        return availableCells

    }

    private fun getScorePredictions(dices : Collection<Dice>) : Collection<Score>{

        val scores : ArrayList<Score> = arrayListOf()

        rowHeaders.forEach{scores.add(it.second.getDiceScore(dices))}

        return scores
    }

    fun updatePredictions(dices : Collection<Dice>){
        validCells = getAvailableCellIndices()
        scores = getScorePredictions(dices)
    }

    fun writeToScoreboard(dataIndexX : Int, dataIndexY : Int) : Boolean{

        var isValidCell = false
        validCells.forEach {
            if(it.first == dataIndexX && it.second == dataIndexY)
            {
                isValidCell = true
                return@forEach
            }

        }

        if (isValidCell){
            data[dataIndexX, dataIndexY] = scores.elementAt(dataIndexX)
        }

        return isValidCell
    }

    fun getScoreSum() : Int{
        var sum = 0
        data.forEach { if(it.value != Score.NO_SCORE_VALUE) sum += it.value }
        return sum
    }

    override fun getDisplayStringSet(): String {

        var outputString = "\t\t\t|"

        columnHeaders.forEach{
            outputString += "${it.first}\t|"
        }
        outputString += "\n"

        scores.forEachIndexed{index, score ->

            var startIndex = 0
            outputString += rowHeaders[index].first + if(rowHeaders[index].first.length >= 8) "\t|" else "\t\t|"

            val validCellsInRow = validCells.filter{ it.second == index }

            if(validCellsInRow.isNotEmpty())
                validCellsInRow.forEach {

                    for (i in startIndex until it.first step 1)
                        outputString += "${data[i, index]}\t|"

                    startIndex = it.first+1
                    outputString += "$score\t|"
                }

            else
                for(i in 0 until data.sizeX step 1)
                    outputString += "${data[i, index]}\t"

            outputString += "\n"

        }

        return outputString
    }

}