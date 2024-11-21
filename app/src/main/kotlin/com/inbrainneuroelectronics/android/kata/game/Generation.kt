package com.inbrainneuroelectronics.android.kata.game

data class Generation(
    val rows: Int,
    val columns: Int,
    val members: Map<Position, Cell>
) {

    init {
        require(
            members.filterKeys {
                it.row > rows || it.column > columns
            }.isEmpty()
        ) {
            "Dimensions of the grid do not correspond to the members"
        }
        require(
            members.filterValues {
                it.isCellAlive
            }.isNotEmpty()
        ) {
            "The generation should have at least one live cell"
        }
        //todo we have to calculate the minimum grid size for the game to work

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Generation

        if (rows != other.rows) return false
        if (columns != other.columns) return false
        if (members.entries.size != other.members.entries.size) return false
        members.forEach { (position, cell) ->
            val key = other.members.keys.find {
                it.row == position.row && it.column == position.column
            }
            val otherCell = other.members[key]
            if (otherCell?.isCellAlive != cell.isCellAlive) {
                return false
            }
        }
        return true
    }

    override fun hashCode(): Int {
        var result = rows
        result = 31 * result + columns
        result = 31 * result + members.hashCode()
        return result
    }

}