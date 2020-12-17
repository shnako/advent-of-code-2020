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

fun are2DCoordinatesValid(x: Int, y: Int, grid2D: Array<CharArray>): Boolean {
    if (x < 0 || y < 0) {
        return false
    }

    if (x >= grid2D.size || y >= grid2D[x].size) {
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

// region 4D

val neighbours4D = arrayOf(
    arrayOf(-1, -1, -1, -1),
    arrayOf(-1, -1, -1, +0),
    arrayOf(-1, -1, -1, +1),

    arrayOf(-1, -1, +0, -1),
    arrayOf(-1, -1, +0, +0),
    arrayOf(-1, -1, +0, +1),

    arrayOf(-1, -1, +1, -1),
    arrayOf(-1, -1, +1, +0),
    arrayOf(-1, -1, +1, +1),


    arrayOf(-1, +0, -1, -1),
    arrayOf(-1, +0, -1, +0),
    arrayOf(-1, +0, -1, +1),

    arrayOf(-1, +0, +0, -1),
    arrayOf(-1, +0, +0, +0),
    arrayOf(-1, +0, +0, +1),

    arrayOf(-1, +0, +1, -1),
    arrayOf(-1, +0, +1, +0),
    arrayOf(-1, +0, +1, +1),


    arrayOf(-1, +1, -1, -1),
    arrayOf(-1, +1, -1, +0),
    arrayOf(-1, +1, -1, +1),

    arrayOf(-1, +1, +0, -1),
    arrayOf(-1, +1, +0, +0),
    arrayOf(-1, +1, +0, +1),

    arrayOf(-1, +1, +1, -1),
    arrayOf(-1, +1, +1, +0),
    arrayOf(-1, +1, +1, +1),


    arrayOf(+0, -1, -1, -1),
    arrayOf(+0, -1, -1, +0),
    arrayOf(+0, -1, -1, +1),

    arrayOf(+0, -1, +0, -1),
    arrayOf(+0, -1, +0, +0),
    arrayOf(+0, -1, +0, +1),

    arrayOf(+0, -1, +1, -1),
    arrayOf(+0, -1, +1, +0),
    arrayOf(+0, -1, +1, +1),


    arrayOf(+0, +0, -1, -1),
    arrayOf(+0, +0, -1, +0),
    arrayOf(+0, +0, -1, +1),

    arrayOf(+0, +0, +0, -1),
    arrayOf(+0, +0, +0, +1),

    arrayOf(+0, +0, +1, -1),
    arrayOf(+0, +0, +1, +0),
    arrayOf(+0, +0, +1, +1),


    arrayOf(+0, +1, -1, -1),
    arrayOf(+0, +1, -1, +0),
    arrayOf(+0, +1, -1, +1),

    arrayOf(+0, +1, +0, -1),
    arrayOf(+0, +1, +0, +0),
    arrayOf(+0, +1, +0, +1),

    arrayOf(+0, +1, +1, -1),
    arrayOf(+0, +1, +1, +0),
    arrayOf(+0, +1, +1, +1),


    arrayOf(+1, -1, -1, -1),
    arrayOf(+1, -1, -1, +0),
    arrayOf(+1, -1, -1, +1),

    arrayOf(+1, -1, +0, -1),
    arrayOf(+1, -1, +0, +0),
    arrayOf(+1, -1, +0, +1),

    arrayOf(+1, -1, +1, -1),
    arrayOf(+1, -1, +1, +0),
    arrayOf(+1, -1, +1, +1),


    arrayOf(+1, +0, -1, -1),
    arrayOf(+1, +0, -1, +0),
    arrayOf(+1, +0, -1, +1),

    arrayOf(+1, +0, +0, -1),
    arrayOf(+1, +0, +0, +0),
    arrayOf(+1, +0, +0, +1),

    arrayOf(+1, +0, +1, -1),
    arrayOf(+1, +0, +1, +0),
    arrayOf(+1, +0, +1, +1),


    arrayOf(+1, +1, -1, -1),
    arrayOf(+1, +1, -1, +0),
    arrayOf(+1, +1, -1, +1),

    arrayOf(+1, +1, +0, -1),
    arrayOf(+1, +1, +0, +0),
    arrayOf(+1, +1, +0, +1),

    arrayOf(+1, +1, +1, -1),
    arrayOf(+1, +1, +1, +0),
    arrayOf(+1, +1, +1, +1),
)

fun are4DCoordinatesValid(x: Int, y: Int, z: Int, w: Int, grid4D: Array<Array<Array<BooleanArray>>>): Boolean {
    if (x < 0 || y < 0 || z < 0 || w < 0) {
        return false
    }

    if (x >= grid4D.size || y >= grid4D[x].size || z >= grid4D[x][y].size || w >= grid4D[x][y][z].size) {
        return false
    }

    return true
}

// endregion
