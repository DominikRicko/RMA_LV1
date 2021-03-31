package yamb.scores

import yamb.dice.Dice
import yamb.dice.SixSidedDice
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ScoreRuleFullTest{

    lateinit var dices : ArrayList<Dice>

    @BeforeEach
    fun diceCollectionCreation(){
        this.dices = arrayListOf(
            SixSidedDice(), SixSidedDice(), SixSidedDice(),
            SixSidedDice(), SixSidedDice(), SixSidedDice()
        )
    }

    @Test
    fun testSameSide(){

        this.dices.forEach{it.side = 1}

        val score = ScoreRuleFull.getDiceScore(this.dices)

        assertEquals(0, score.value)

    }

    @Test
    fun testTwoFourCombination(){

        for( i in 0..1)
            this.dices[i].side = 2

        for( i in 2..5)
            this.dices[i].side = 3

        val score = ScoreRuleFull.getDiceScore(this.dices)

        assertEquals(43, score.value)

    }

    @Test
    fun testFourTwoCombination(){

        for( i in 0..3)
            this.dices[i].side = 3

        for( i in 4..5)
            this.dices[i].side = 2

        val score = ScoreRuleFull.getDiceScore(this.dices)

        assertEquals(43, score.value)

    }

    @Test
    fun testFiveOneCombination(){

        this.dices[5].side = 1

        for( i in 0..4)
            this.dices[i].side = 5

        val score = ScoreRuleFull.getDiceScore(this.dices)

        assertEquals(0, score.value)

    }

    @Test
    fun testThreeTwoOneCombination(){

        for( i in 0..2)
            this.dices[i].side = 3

        for( i in 3..4)
            this.dices[i].side = 2

        this.dices[5].side = 1

        val score = ScoreRuleFull.getDiceScore(this.dices)

        assertEquals(43, score.value)

    }

    @Test
    fun testThreeThreeCombination(){

        for(i in 0..2)
            this.dices[i].side = 3

        for(i in 3..5)
            this.dices[i].side = 4

        val score = ScoreRuleFull.getDiceScore(this.dices)

        assertEquals(48, score.value)

    }

    @Test
    fun testThreeThreeReversedCombination(){

        for(i in 0..2)
            this.dices[i].side = 4

        for(i in 3..5)
            this.dices[i].side = 3

        val score = ScoreRuleFull.getDiceScore(this.dices)

        assertEquals(48, score.value)

    }
}