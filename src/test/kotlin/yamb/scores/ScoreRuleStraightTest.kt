package yamb.scores

import dice.Dice
import dice.SixSidedDice
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScoreRuleStraightTest{

    lateinit var dices : ArrayList<Dice>

    @BeforeEach
    fun createDicesBeforeTest(){
        this.dices = arrayListOf(
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice()
        )
    }

    @Test
    fun testLargeStraight(){

        this.dices[0].side = 2
        this.dices[2].side = 3
        this.dices[1].side = 5
        this.dices[3].side = 4
        this.dices[4].side = 6
        this.dices[5].side = 6

        val score = ScoreRuleStraight.getDiceScore(this.dices)

        assertEquals(40, score.value)

    }

    @Test
    fun testSmallStraight(){

        this.dices[0].side = 2
        this.dices[2].side = 3
        this.dices[1].side = 5
        this.dices[3].side = 4
        this.dices[4].side = 1
        this.dices[5].side = 1

        val score = ScoreRuleStraight.getDiceScore(this.dices)

        assertEquals(30, score.value)

    }

    @Test
    fun testLargeStraightOverSmallStraight(){

        this.dices[0].side = 2
        this.dices[2].side = 3
        this.dices[1].side = 5
        this.dices[3].side = 4
        this.dices[4].side = 6
        this.dices[5].side = 1

        val score = ScoreRuleStraight.getDiceScore(this.dices)

        assertEquals(40, score.value)

    }

    @Test
    fun testNoStraight(){

        this.dices[0].side = 2
        this.dices[2].side = 3
        this.dices[1].side = 5
        this.dices[3].side = 6
        this.dices[4].side = 6
        this.dices[5].side = 6

        val score = ScoreRuleStraight.getDiceScore(this.dices)

        assertEquals(0, score.value)

    }
}