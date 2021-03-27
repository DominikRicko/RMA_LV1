package dice

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.asserter

internal class SixSidedDiceTest {

    @Test
    fun testSetSideWithinRange() {
        val dice : Dice = SixSidedDice()

        dice.side = 5

        kotlin.test.assertEquals(5, dice.side)
    }

    @Test
    fun testSetSideOutsideRange(){
        val dice: Dice = SixSidedDice()
        val initialValue = dice.side

        dice.side = 7

        kotlin.test.assertEquals(initialValue, dice.side)
    }

    @Test
    fun testLocking(){

        val dice: Dice = SixSidedDice()

        dice.side = 1
        dice.locked = true
        dice.side = 3

        assertEquals(1, dice.side)
    }
    //Testing roll would sometimes cause it to fail because of Random
    //Cannot vouch that the dice side before rolling will be different after rolling
}