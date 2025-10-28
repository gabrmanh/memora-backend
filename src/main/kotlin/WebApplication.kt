package org.showoff

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["org.showoff"])
@EnableJpaRepositories("org.showoff.persistence.repository")
@EntityScan("org.showoff.entity")
open class WebApplication 

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}
