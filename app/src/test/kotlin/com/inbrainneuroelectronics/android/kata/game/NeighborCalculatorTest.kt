package com.inbrainneuroelectronics.android.kata.game

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime

class NeighborCalculatorTest {

    val subject: INeighborCalculator = NeighborCalculator()

    @Test
    fun `returns failure if grid's row value is not valid`() = runBlocking {
        val position = Position(row = 0, column = 4)
        var gridRows = 0
        var gridCols = 3
        var calculatedNeighborsListResult = subject.getNeighborsPositionsFor(position, gridRows, gridCols)
        assert(calculatedNeighborsListResult.isFailure)
        gridRows = 3
        gridCols = 0
        calculatedNeighborsListResult = subject.getNeighborsPositionsFor(position, gridRows, gridCols)
        assert(calculatedNeighborsListResult.isFailure)
    }

    @Test
    fun `returns failure if position of cell is not within grid's boundaries`() = runBlocking {
        var position = Position(row = 0, column = 4)
        val gridRows = 3
        val gridCols = 3
        var calculatedNeighborsListResult = subject.getNeighborsPositionsFor(position, gridRows, gridCols)
        assert(calculatedNeighborsListResult.isFailure)
        position = Position(row = 1, column = 0)
        calculatedNeighborsListResult = subject.getNeighborsPositionsFor(position, gridRows, gridCols)
        assert(calculatedNeighborsListResult.isFailure)
    }

    @Test
    fun `returns failure if position of cell is bigger then the grid`() = runBlocking {
        val position = Position(row = 1, column = 4)
        val gridRows = 3
        val gridCols = 3
        val calculatedNeighborsListResult = subject.getNeighborsPositionsFor(position, gridRows, gridCols)
        assert(calculatedNeighborsListResult.isFailure)
    }

    @Test
    fun `gets correctly the list of neighbors in 3x3 first position`() = runBlocking {
        val position = Position(row = 1, column = 1)
        val gridRows = 3
        val gridCols = 3
        //neighbors should be (1,2) (2,1) and (2,2)
        val expectedNeighborsList = listOf(
            Position(1,2),
            Position(2,1),
            Position(2,2)
        )
        val calculatedNeighborsList = subject.getNeighborsPositionsFor(position, gridRows, gridCols).getOrThrow()
        for (currentNeighbor in calculatedNeighborsList) {
            val currentExpectedNeighbor = expectedNeighborsList.find {
                it.row == currentNeighbor.row &&
                        it.column == currentNeighbor.column }
            assert(currentExpectedNeighbor != null)
        }
    }

    @Test
    fun `gets correctly the list of neighbors in 5x4 edge position`() = runBlocking {
        val position = Position(row = 1, column = 5)
        val gridRows = 4
        val gridCols = 5
        val expectedNeighborsList = listOf(
            Position(1,4),
            Position(2,4),
            Position(2,5)
        )
        val calculatedNeighborsList = subject.getNeighborsPositionsFor(position, gridRows, gridCols).getOrThrow()
        for (currentNeighbor in calculatedNeighborsList) {
            val currentExpectedNeighbor = expectedNeighborsList.find {
                it.row == currentNeighbor.row &&
                        it.column == currentNeighbor.column }
            assert(currentExpectedNeighbor != null)
        }
    }

    @Test
    fun `gets correctly the list of neighbors in 5x5 center position`() = runBlocking {
        val position = Position(row = 2, column = 3)
        val gridRows = 5
        val gridCols = 5
        val expectedNeighborsList = listOf(
            Position(1,2),
            Position(1,3),
            Position(1,4),
            Position(2,2),
            Position(2,4),
            Position(3,2),
            Position(3,3),
            Position(3,4)
        )
        val calculatedNeighborsList = subject.getNeighborsPositionsFor(position, gridRows, gridCols).getOrThrow()
        for (currentNeighbor in calculatedNeighborsList) {
            val currentExpectedNeighbor = expectedNeighborsList.find {
                it.row == currentNeighbor.row &&
                        it.column == currentNeighbor.column }
            assert(currentExpectedNeighbor != null)
        }
    }

    @Test
    fun `second calculation takes less time than first one`() = runBlocking {
        val position = Position(row = 2, column = 3)
        val gridRows = 5
        val gridCols = 5
        val firstCallAverage = mutableListOf<Long>()
        val secondCallAverage = mutableListOf<Long>()
        repeat(500) {
            val calc = NeighborCalculator()
            val firstCallTime = measureNanoTime {
                calc.getNeighborsPositionsFor(position, gridRows, gridCols).getOrThrow()
            }
            firstCallAverage.add(firstCallTime)

            val secondCallTime = measureNanoTime {
                calc.getNeighborsPositionsFor(position, gridRows, gridCols).getOrThrow()
            }
            secondCallAverage.add(secondCallTime)
        }
        assert(secondCallAverage.average() < firstCallAverage.average())
    }

}