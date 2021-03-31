package yamb.scores

object ScoreColumnRuleTopDown : ScoreColumnRule {
    override fun getAvailableIndices(column: Collection<Int>): Collection<Int> {
        return listOf(column.indexOfFirst {it == -1})
    }
}