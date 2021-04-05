package cards

class Card(val rank: Rank, val suit: Suit) {

    enum class Suit(val suitText: String) {
        Hearts("Hearts"),
        Clubs("Clubs"),
        Diamonds("Diamonds"),
        Spades("Spades");

        override fun toString(): String {
            return this.suitText
        }
    }

    enum class Rank(val rankText: String, val value: Int) {
        Ace("Ace", 1),
        Two("Two", 2),
        Three("Three", 3),
        Four("Four", 4),
        Five("Five", 5),
        Six("Six", 6),
        Seven("Seven", 7),
        Eight("Eight", 8),
        Nine("Nine", 9),
        Ten("Ten", 10),
        Jack("Jack", 11),
        Queen("Queen", 12),
        King("King", 13);

        override fun toString(): String {
            return this.rankText
        }
    }


}