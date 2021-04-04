package dice

interface Dice {
    var locked: Boolean
    var side: Int
    fun roll()
}