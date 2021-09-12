package kost.romi.bookmarktwitchchat

import android.graphics.Color
import org.junit.Test
import java.util.*

class RandomColorGeneratorTest {

    @Test
    fun main() {
        for (i in 1..10) {
            print("Random color: ${generateColor()}")
        }
    }

    fun generateColor(): Int {
        var random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

}