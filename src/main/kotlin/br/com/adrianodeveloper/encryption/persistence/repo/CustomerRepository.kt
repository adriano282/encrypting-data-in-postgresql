package br.com.adrianodeveloper.encryption.persistence.repo

import br.com.adrianodeveloper.encryption.persistence.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long>