package com.inbrainneuroelectronics.android.kata.game

interface IGenerationCalculator {
    suspend fun getNextGeneration(generation: Generation): Result<Generation>
}

class GenerationCalculator(
    private val neighborCalculator: INeighborCalculator,
    private val lifeProvider: ILifeProvider
): IGenerationCalculator {

    override suspend fun getNextGeneration(generation: Generation): Result<Generation> {
        val nextGenerationMembers = mutableMapOf<Position, Cell>()
        for (col in 1..generation.columns) {
            for (row in 1..generation.rows) {
                val position = Position(row = row, column = col)
                val key = generation.members.keys.find {
                    it.row == position.row && it.column == position.column
                } ?: return Result.failure(Exception("An error occurred obtaining key in position $position"))
                val cell = generation.members[key] ?: return Result.failure(Exception("An error occurred obtaining cell in position $position"))
                val neighborCalcResult = neighborCalculator.getNeighborsPositionsFor(
                    position = position,
                    cols = generation.columns,
                    rows = generation.rows
                )
                if (neighborCalcResult.isFailure) {
                    return Result.failure(neighborCalcResult.exceptionOrNull() ?: Exception("An error occurred calculating neighbors")) //todo create a custom exception
                } else {
                    val neighborPositions = neighborCalcResult.getOrThrow()
                    val totalAliveNeighbors = neighborCalculator.getTotalAliveNeighborsFor(neighborPositions, generation)
                    val isCellAliveInNextGenerationResult = lifeProvider.willItLiveOrDie(cell, totalAliveNeighbors)
                    if (isCellAliveInNextGenerationResult.isSuccess) {
                        nextGenerationMembers[position] = Cell(isCellAlive = isCellAliveInNextGenerationResult.getOrThrow())
                    } else {
                        return Result.failure(isCellAliveInNextGenerationResult.exceptionOrNull() ?: Exception("An error occurred deciding on a cell's life"))
                    }
                }
            }
        }
        val nextGeneration = Generation(
            rows = generation.rows,
            columns = generation.columns,
            members = nextGenerationMembers
        )
        return Result.success(nextGeneration)
    }

}