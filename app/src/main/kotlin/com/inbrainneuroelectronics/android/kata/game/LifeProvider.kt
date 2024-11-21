package com.inbrainneuroelectronics.android.kata.game

interface ILifeProvider {
    fun willItLiveOrDie(cell: Cell, totalAliveNeighbors: Int): Result<Boolean>
}

class LifeProvider : ILifeProvider {

    //Live = true
    //Die = false
    override fun willItLiveOrDie(
        cell: Cell,
        totalAliveNeighbors: Int
    ) : Result<Boolean> {
        if (totalAliveNeighbors < 0) {
            return Result.failure(IllegalArgumentException("Can't have a negative number of neighbors"))
        }
        return when {
            cell.isCellAlive && (totalAliveNeighbors in 2..3) -> Result.success(true)
            !cell.isCellAlive && totalAliveNeighbors == 3 -> Result.success(true)
            else -> Result.success(false)
        }
    }

}