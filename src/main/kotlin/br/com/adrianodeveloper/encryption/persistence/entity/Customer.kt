package br.com.adrianodeveloper.encryption.persistence.entity

import org.hibernate.annotations.ColumnTransformer
import javax.persistence.*

@Entity
@Table(name = "customer")
data class Customer(

        @Column val name: String,

        @Column val username: String,

        @Column(columnDefinition = "bytea")
        @ColumnTransformer(
                read    = "pgp_pub_decrypt(secret, dearmor(current_setting('keys.private')), current_setting('decryption.password'))",
                write   = "pgp_pub_encrypt(?, dearmor(current_setting('keys.public')))")
        val secret: String,

        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id @Column var id: Long?) {

        constructor(name: String, username: String, password: String) : this(name, username, password, null)
}