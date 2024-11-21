package com.inbrainneuroelectronics.android.kata.game

import androidx.annotation.VisibleForTesting

interface INeighborCalculator {

    suspend fun getNeighborsPositionsFor(position: Position, rows: Int, cols: Int) : Result<List<Position>>
    suspend fun getTotalAliveNeighborsFor(neighborsPositionList: List<Position>, generation: Generation): Int
}

class NeighborCalculator : INeighborCalculator {

    //We store the calculated neighbors positions in memory so we have faster
    //iterations of a generation of a given size
    private val inMemoryCalculatedNeighbors: MutableMap<Pair<Int, Int>, Map<Position, MutableList<Position>>> = mutableMapOf()

    //We dont use zero-index. First element is (1,1)
    override suspend fun getNeighborsPositionsFor(
        position: Position,
        rows: Int,
        cols: Int
    ): Result<List<Position>> {
        if (position.row < 1 || position.column < 1) {
            return Result.failure(IllegalArgumentException("Position out of boundaries"))
        }
        if (rows < 1 || cols < 1) {
            return Result.failure(IllegalArgumentException("We need a valid grid"))
        }
        if (position.row > rows || position.column > cols) {
            return Result.failure(IllegalArgumentException("Position is not within the grid limits"))
        }
        val grid = Pair(rows, cols)

        val inMemoryList = inMemoryCalculatedNeighbors[grid]?.entries?.find {
            it.key.row == position.row && it.key.column == position.column
        }?.value

        //Get from memory or calculate new
        if (inMemoryList != null) {
            return Result.success(inMemoryList)
        } else {
            val neighbors = mutableListOf<Position>()

            for (currentRow in position.row - 1..position.row + 1) {
                if (currentRow < 1) continue
                if (currentRow > rows) continue
                for (currentCol in position.column - 1 .. position.column + 1) {
                    if (currentCol < 1) continue
                    if (currentCol > cols) continue
                    if (currentCol == position.column && currentRow == position.row) continue
                    neighbors.add(Position(row = currentRow, column = currentCol))
                }
            }

            inMemoryCalculatedNeighbors[grid] = mapOf(Pair(position, neighbors))
            return Result.success(neighbors)
        }
    }

    override suspend fun getTotalAliveNeighborsFor(
        neighborsPositionList: List<Position>,
        generation: Generation
    ): Int {
        var aliveCount = 0
        for (neighborPosition in neighborsPositionList) {
            generation.members.entries.find {
                it.key.row == neighborPosition.row &&
                        it.key.column == neighborPosition.column
            }?.value?.let { cell ->
                if (cell.isCellAlive) {
                    aliveCount++
                }
            }
        }
        return aliveCount
    }

}