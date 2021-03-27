package scores

import dice.Dice
import dice.SixSidedDice
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScoreRulePokerTest{

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
    fun testSameSide(){

        this.dices.forEach{ it.side = 1}

        val score = ScoreRulePoker.getDiceScore(this.dices)

        assertEquals(45, score)

    }

    @Test
    fun testFiveOneCombination(){

        for (i in 0..4)
            this.dices[i].side = 4

        this.dices[5].side = 5

        val score = ScoreRulePoker.getDiceScore(this.dices)

        assertEquals(61, score)

    }

    @Test
    fun testOneFiveCombination(){

        for (i in 1..5)
            this.dices[i].side = 5

        this.dices[0].side = 1

        val score = ScoreRulePoker.getDiceScore(this.dices)

        assertEquals(65, score)

    }

    @Test
    fun testThreeThreeCombination(){

        for (i in 0..2)
            this.dices[i].side = 4

        for(i in 3..5)
            this.dices[i].side = 2

        val score = ScoreRulePoker.getDiceScore(this.dices)

        assertEquals(0, score)

    }

    @Test
    fun testFourOneOneCombination(){

        for (i in 0..3)
            this.dices[i].side = 4

        this.dices[4].side = 2
        this.dices[5].side = 1

        val score = ScoreRulePoker.getDiceScore(this.dices)

        assertEquals(58, score)

    }

}