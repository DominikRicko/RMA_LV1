package yamb.scores

object ScoreColumnRuleTopDown : ScoreColumnRule {
    override fun getAvailableIndices(column: Collection<Score>): Collection<Int> {
        return listOf(column.indexOfFirst { it.value == Score.NO_SCORE_VALUE })
    }
}