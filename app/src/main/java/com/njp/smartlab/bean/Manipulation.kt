package com.njp.smartlab.bean

data class Manipulation(
        val manipulationId: Int,
        val userId: String,
        val time: Long,
        val functionType: Int,
        val boxId: Int
)