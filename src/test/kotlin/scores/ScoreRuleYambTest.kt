package scores

import dice.Dice
import dice.SixSidedDice
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScoreRuleYambTest{

    lateinit var dices : ArrayList<Dice>

    @BeforeEach
    fun createDicesBeforeTest(){
        this.dices = arrayListOf(
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
            SixSidedDice(),
        )

    }

    @Test
    fun testSameSide(){

        this.dices.forEach{
            it.side = 1
        }

        val score = ScoreRuleYamb.getDiceScore(this.dices)

        assertEquals(55, score)

    }

    @Test
    fun testOneFiveCombination(){

        for (i in 0..4)
            this.dices[i].side = 5

        this.dices[5].side = 1

        val score = ScoreRuleYamb.getDiceScore(this.dices)

        assertEquals(75, score)

    }

    @Test
    fun testTwoFourCombination(){

        for (i in 0..3)
            this.dices[i].side = 5

        for (i in 4..5)
            this.dices[i].side = 1

        val score = ScoreRuleYamb.getDiceScore(this.dices)

        assertEquals(0, score)

    }
}