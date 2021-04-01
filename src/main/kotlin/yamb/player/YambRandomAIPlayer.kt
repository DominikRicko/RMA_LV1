package yamb.player

import dice.Dice

class YambRandomAIPlayer(override val name: String) : YambPlayer {

    companion object{

        private var counter = 0

        operator fun invoke(): YambRandomAIPlayer = YambRandomAIPlayer("AI $counter++")

    }

    override fun doPlayerTurn() {
        TODO("Not yet implemented")
    }

    override fun giveDices(dices: Collection<Dice>) {
        TODO("Not yet implemented")
    }

    override fun getTotalScore(): Int {
        TODO("Not yet implemented")
    }
}