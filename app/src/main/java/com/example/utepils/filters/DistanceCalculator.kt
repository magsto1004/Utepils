package com.example.utepils.filters

import android.location.LocationManager
import android.util.FloatMath
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.acos


class DistanceCalculator {



    fun measure(lat_a: Double, lng_a: Double, lat_b: Double, lng_b: Double): Double {
        val pk = (180 / 3.14169).toFloat()
        val a1 = lat_a / pk
        val a2 = lng_a / pk
        val b1 = lat_b / pk
        val b2 = lng_b / pk
        val t1: Double =
            kotlin.math.cos(a1) * kotlin.math.cos(a2) * kotlin.math.cos(b1) * kotlin.math.cos(b2)
        val t2: Double =
            kotlin.math.cos(a1) * kotlin.math.sin(a2) * kotlin.math.cos(b1) * kotlin.math.sin(b2)
        val t3: Double = kotlin.math.sin(a1) * kotlin.math.sin(b1)
        val tt = acos((t1 + t2 + t3).toDouble())
        return 6366000 * tt
    }
}