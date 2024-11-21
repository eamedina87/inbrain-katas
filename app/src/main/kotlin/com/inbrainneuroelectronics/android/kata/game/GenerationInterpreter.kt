package com.inbrainneuroelectronics.android.kata.game

interface IGenerationInterpreter {
    fun createGenerationFromChars(inputGrid: List<String>) : Result<Generation>
    fun createCharsFromGeneration(generation: Generation) : Result<List<String>>
}

class GenerationInterpreter : IGenerationInterpreter {


    /***
     *  The list of strings provided should correspond to a grid with given nxm dimensions
     *  A dot represents a dead cell. An asterisk represents a live cell like in the following sample:
     *
     *         "........",
     *         "....*...",
     *         "...**...",
     *         "........"
     *
     */
    override fun createGenerationFromChars(inputGrid: List<String>): Result<Generation> {
        if (inputGrid.isEmpty()) {
            return Result.failure(IllegalArgumentException("The grid can't be empty"))
        }
        val initialLength = inputGrid[0].length
        inputGrid.forEachIndexed { index, text ->
            if (text.isEmpty()) {
                return Result.failure(IllegalArgumentException("The grid must have values in element [$index]"))
            }
            if (text.length != initialLength) {
                return Result.failure(IllegalArgumentException("The grid must have have the same length in all fields. Failed in element:[$index]"))
            }
        }

        val grid = mutableMapOf<Position, Cell>()
        for ((rowIndex, row) in inputGrid.withIndex()) {
            for ((colIndex, char) in row.withIndex()) {
                val position = Position(rowIndex + 1, colIndex + 1) // Position starts at (1,1)
                val cell = Cell(char == '*')
                grid[position] = cell
            }
        }

        val generation = Generation(rows = inputGrid.size, columns = initialLength, members = grid)
        return Result.success(generation)
    }

    override fun createCharsFromGeneration(generation: Generation): Result<List<String>> {
        TODO("Not yet implemented")
    }
}