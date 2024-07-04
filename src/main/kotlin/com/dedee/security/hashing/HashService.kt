package com.dedee.security.hashing


interface HashService {

    suspend fun generateSaltedHash(value: String, saltLength: Int = 32): SaltedHash
    suspend fun verify(value: String, salt: String, hashedPassword: String): Boolean

}