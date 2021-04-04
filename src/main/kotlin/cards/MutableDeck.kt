package cards

class MutableDeck : Deck{

    private val cards : ArrayList<Card> = arrayListOf()

    fun addCard(card: Card){

        if(!cards.contains(card))
            cards.add(card)

    }

    override fun shuffle() {
        cards.shuffle()
    }

    override fun drawCard(): Card {
        val card = cards[0]
        cards.removeAt(0)
        return card
    }
}