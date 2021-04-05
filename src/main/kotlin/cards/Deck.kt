package cards

interface Deck {

    fun shuffle()
    fun drawCard(): Card
    fun getSize(): Int

}