package yamb.scores

data class Score(var value: Int) {

    companion object {
        const val NO_SCORE_VALUE = -1
    }

    override fun toString(): String {
        return if (value == NO_SCORE_VALUE)
            "-"
        else
            value.toString()
    }
}
