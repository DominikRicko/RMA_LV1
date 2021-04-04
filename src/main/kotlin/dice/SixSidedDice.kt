package dice

class SixSidedDice : Dice {

    override var locked: Boolean = false

    override var side: Int = (1..6).random()
        set(value) {

            if (value in 1..6 && !this.locked)
                field = value

        }

    override fun roll() {

        this.side = (1..6).random()

    }

}