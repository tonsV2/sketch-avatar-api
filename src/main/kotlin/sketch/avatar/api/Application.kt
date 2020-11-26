package sketch.avatar.api

import io.micronaut.runtime.Micronaut

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("sketch.avatar.api")
                .mainClass(Application.javaClass)
                .start()
    }
}
