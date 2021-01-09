package pl.marconzet.spotset

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpotSetAlgebraApplication

fun main(args: Array<String>) {
	runApplication<SpotSetAlgebraApplication>(*args)
}

inline fun <reified T> T.logger(): Logger {
	return LoggerFactory.getLogger(T::class.java)
}
