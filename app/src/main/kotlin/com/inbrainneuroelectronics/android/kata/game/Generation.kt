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

}