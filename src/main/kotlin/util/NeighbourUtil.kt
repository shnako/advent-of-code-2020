package util

// region 2D

val neighbours2D = arrayOf(
    arrayOf(-1, -1),
    arrayOf(-1, +0),
    arrayOf(-1, +1),

    arrayOf(+0, -1),
    arrayOf(+0, +1),

    arrayOf(+1, -1),
    arrayOf(+1, +0),
    arrayOf(+1, +1),
)

fun are2DCoordinatesValid(coordinates: Array<Int>, grid2D: Array<CharArray>): Boolean {
    if (coordinates[0] < 0 || coordinates[1] < 0) {
        return false
    }

    if (coordinates[0] >= grid2D.size || coordinates[1] >= grid2D[0].size) {
        return false
    }

    return true
}

// endregion

// region 3D

val neighbours3D = arrayOf(
    arrayOf(-1, -1, -1),
    arrayOf(-1, -1, +0),
    arrayOf(-1, -1, +1),
    arrayOf(-1, +0, -1),
    arrayOf(-1, +0, +0),
    arrayOf(-1, +0, +1),
    arrayOf(-1, +1, -1),
    arrayOf(-1, +1, +0),
    arrayOf(-1, +1, +1),

    arrayOf(+0, -1, -1),
    arrayOf(+0, -1, +0),
    arrayOf(+0, -1, +1),
    arrayOf(+0, +0, -1),
    arrayOf(+0, +0, +1),
    arrayOf(+0, +1, -1),
    arrayOf(+0, +1, +0),
    arrayOf(+0, +1, +1),

    arrayOf(+1, -1, -1),
    arrayOf(+1, -1, +0),
    arrayOf(+1, -1, +1),
    arrayOf(+1, +0, -1),
    arrayOf(+1, +0, +0),
    arrayOf(+1, +0, +1),
    arrayOf(+1, +1, -1),
    arrayOf(+1, +1, +0),
    arrayOf(+1, +1, +1),
)

fun are3DCoordinatesValid(x: Int, y: Int, z: Int, grid3D: Array<Array<BooleanArray>>): Boolean {
    if (x < 0 || y < 0 || z < 0) {
        return false
    }

    if (x >= grid3D.size || y >= grid3D[x].size || z >= grid3D[x][y].size) {
        return false
    }

    return true
}

// endregion
