package yamb.scores

object ScoreColumnRuleBottomUp : ScoreColumnRule {
    override fun getAvailableIndices(column: Collection<Int>): Collection<Int> {

        return listOf(column.indexOfLast{ it==-1 })

    }
}