package org.showoff.presentation.config

import org.showoff.persistence.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
open class UserDetailsConfig(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Bean
    open fun userDetailsService(): UserDetailsService {
        return UserDetailsService { email ->
            val user = userRepository.findByEmail(email)
                ?: throw IllegalArgumentException("User not found")

            User.withUsername(user.email)
                .password(user.passwordHash)
                .roles("USER")
                .build()
        }
    }
}