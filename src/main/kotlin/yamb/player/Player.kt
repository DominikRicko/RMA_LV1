package yamb.player

import yamb.scores.Scoreboard

class Player(val name: String) {
    val scoreboard : Scoreboard = Scoreboard()

    override fun toString(): String {
        return name
    }
}