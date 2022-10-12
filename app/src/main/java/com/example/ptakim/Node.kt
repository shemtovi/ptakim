package com.example.ptakim

class Node {
    var num: Int
        private set
    var str: String
        private set

    constructor(num: Int, str: String) {
        this.num = num
        this.str = str
    }

    constructor(str: String) {
        this.str = str
        num = getRandomIntInclusive(0.0, 10000.0)
    }

    constructor(str: String, min: Int) {
        this.str = str
        num = getRandomIntInclusive(min.toDouble(), 10000.0)
    }

    private fun getRandomIntInclusive(min: Double, max: Double): Int {
        var min = min
        var max = max
        min = Math.ceil(min)
        max = Math.floor(max)
        return Math.floor(Math.random() * (max - min + 1) + min)
            .toInt() // The maximum is inclusive and the minimum is inclusive
    }
}