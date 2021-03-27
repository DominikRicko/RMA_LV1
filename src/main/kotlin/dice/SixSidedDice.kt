package dice

import kotlin.random.Random

class SixSidedDice() : Dice {

    override var locked: Boolean = false

    override var side: Int = Random.nextInt(1,6)
        set(value) {

            if(value < 1 || value > 6)
                field = value

        }

    override fun Roll() {

        if(!this.locked)
            this.side = Random.nextInt(1,6)

    }

}