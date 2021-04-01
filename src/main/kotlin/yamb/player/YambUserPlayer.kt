package yamb.player

import dice.Dice
import yamb.scores.Score
import yamb.scores.Scoreboard

class YambUserPlayer(override val name: String) : YambPlayer {
    private val scoreboard : Scoreboard = Scoreboard()
    private var diceRolls : Int = 0
    private lateinit var validCells : Collection<Pair<Int,Int>>
    private lateinit var scores : Collection<Score>
    private lateinit var dices : Collection<Dice>

    override fun toString(): String {
        return name
    }

    private fun rollDices(){
        dices.forEach { it.roll() }
    }

    private fun lockDice(whichDice : Int, lock : Boolean){
        dices.elementAt(whichDice).locked = lock
    }

    private fun updatePredictions(){
        validCells = scoreboard.getAvailableCellIndices()
        scores = scoreboard.getScorePredictions(dices)
    }

    private fun writeToScoreboard(dataIndexX : Int, dataIndexY : Int) : Boolean{

        var isValidCell = false
        validCells.forEach {
            if(it.first == dataIndexX && it.second == dataIndexY)
            {
                isValidCell = true
                return@forEach
            }

        }

        if (isValidCell){
            scoreboard.data[dataIndexX, dataIndexY] = scores.elementAt(dataIndexX)
        }

        return isValidCell
    }

    private fun processNextCommand(){

        val input = (readLine()?.split(' ')) ?: return
        val command = input[0]
        val arguments = listOf(input[1], input[2]).map { it.toInt()}

        if (arguments.filter { it >= 0 }.size != arguments.size){
            println("Error parsing command arguments")
            return
        }

        when (command){
            "roll "-> {
                rollDices()
                diceRolls--
                updatePredictions()
            }
            "lock" -> {
                if(input.size >= 2) lockDice(input[1].toInt(), true)
                else println("Lock error: Need to specify which dice")
            }
            "unlock" -> {
                if(input.size >= 2) lockDice(input[1].toInt(), false)
                else println("Unlock error: Need to specify which dice")
            }
            "save" -> {
                if (input.size >= 3) {
                    val success = writeToScoreboard(input[1].toInt(),input[2].toInt())
                    if(success)
                        diceRolls = 0
                }
                else println("Save error: Need to specify matrix indices to save score in that cell")
            }
            else -> println("Unknown command")
        }

    }

    private fun updateDisplay(){

        for(i in 0..9)
            println("")

        print("\t\t")
        Scoreboard.columnHeaders.forEach{
            print("${it.first}\t|")
        }
        print("\n")

        scores.forEachIndexed{index, score ->

            var startIndex = 0
            var outputString = Scoreboard.rowHeaders[index].first + "\t|"

            val validCellsInRow = validCells.filter{ it.second == index }

            if(validCellsInRow.isNotEmpty())
                validCellsInRow.forEach {

                    for (i in startIndex until it.first step 1)
                        outputString += "${scoreboard.data[i, index]}\t|"

                    startIndex = it.first+1
                    outputString += "$score\t|"
                }

            else
                for(i in 0 until scoreboard.data.sizeX step 1)
                    outputString += "${scoreboard.data[i, index]}\t"

            println(outputString)

        }

        println("Commands are: sel num1 num2 - to fill in the spot on the scoreboard")
        println("\t\tlock diceNumber - to lock a particular dice")
        println("\t\tunlock diceNumber -to unlock a particular dice")
        println("\t\troll - to roll all unlocked dices")

        dices.forEachIndexed{ index, dice ->
            println("Dice $index: ${if (dice.locked) "locked" else "unlocked"} side: ${dice.side}")
        }

    }

    override fun doPlayerTurn() {
        diceRolls = 3
        updatePredictions()

        while(diceRolls > 0){
            updateDisplay()
            processNextCommand()
        }

    }

    override fun giveDices(dices: Collection<Dice>) {
        this.dices = dices
    }

    override fun getTotalScore() : Int{
        return scoreboard.getScoreSum()
    }
}