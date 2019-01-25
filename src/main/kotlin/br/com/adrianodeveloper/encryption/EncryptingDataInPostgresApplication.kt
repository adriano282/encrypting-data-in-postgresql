package br.com.adrianodeveloper.encryption

import br.com.adrianodeveloper.encryption.persistence.entity.Customer
import br.com.adrianodeveloper.encryption.persistence.repo.CustomerRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@SpringBootApplication
@RestController
class EncryptingDataInPostgresApplication(val customerRepo: CustomerRepository) {

    @PostMapping("/customer")
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO) : ResponseEntity<CustomerDTO> {

        val customer = customerDTO.toCustomer()

        return try {

            customerRepo.save(customer)
            ResponseEntity.status(HttpStatus.CREATED).build()

        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_GATEWAY).build()
        }
    }

    @GetMapping("/customer/{id}")
    fun getCustomer(@PathVariable("id") id: Long) : ResponseEntity<CustomerDTO> {

        return try {
            val customerDTO = CustomerDTO.fromCustomer(customerRepo.findById(id).get())
            ResponseEntity.of(Optional.of(customerDTO))
        } catch (e : Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    data class CustomerDTO(val name: String, val username: String, val password: String, val id: Long?) {
        fun toCustomer() = Customer(name, username, password)
        companion object {
            fun fromCustomer(customer: Customer) = CustomerDTO(customer.name, customer.username, customer.secret, customer.id)
        }
    }
}


fun main(args: Array<String>) {
    runApplication<EncryptingDataInPostgresApplication>(*args)
}


