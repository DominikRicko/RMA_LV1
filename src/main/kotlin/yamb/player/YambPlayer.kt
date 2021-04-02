package yamb.player

import yamb.Yamb

interface YambPlayer {

    val name : String
    val game : Yamb

    fun processNextCommand()

}