package dice

import kotlin.random.Random

class SixSidedDice() : Dice {

    override var locked: Boolean = false

    override var side: Int = (1..6).random()
        set(value) {

            if(value in 1..6)
                field = value

        }

    override fun roll() {

        if(!this.locked)
            this.side = (1..6).random()

    }

}