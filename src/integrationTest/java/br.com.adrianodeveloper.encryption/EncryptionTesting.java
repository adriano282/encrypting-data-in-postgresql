package br.com.adrianodeveloper.encryption;

import br.com.adrianodeveloper.encryption.healthcheck.PostgresHealthCheck;
import br.com.adrianodeveloper.encryption.persistence.entity.Customer;
import br.com.adrianodeveloper.encryption.persistence.repo.CustomerRepository;
import com.palantir.docker.compose.DockerComposeRule;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptionTesting {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule
            .builder()
            .file("src/integrationTest/resources/docker/docker-compose.yml")
            .waitingForService("database", container -> PostgresHealthCheck.canConnectTo(container))
            .build();

    @Autowired private CustomerRepository repo;

    @Autowired private EntityManager em;

    @Test
    public void encryption_when_saving_a_customer_then_the_secret_must_be_encrypted() {
        String rawSecret = "teste123";

        Customer persistedCustomer = repo.save(new Customer("Adriano Jesus", "adriano.jesus", rawSecret));

        byte[] encryptedSecretBytes = retrieveEncryptedCustomerSecret(persistedCustomer.getId());

        assertNotEquals(new String(encryptedSecretBytes), rawSecret);

        assertEquals(decrypt(encryptedSecretBytes), rawSecret);
    }

    @Test
    public void encryption_when_quering_a_customer_the_secret_must_be_decrypted() {
        String rawSecret = "teste999";

        Customer persistedCustomer = repo.save(new Customer("Fulano da Silva", "fulano.silva", rawSecret));

        Optional<Customer> optionalCustomer = repo.findById(persistedCustomer.getId());

        assertEquals(optionalCustomer.get().getSecret(), rawSecret);
    }

    private byte[] retrieveEncryptedCustomerSecret(Long customerId) {
        Query query = em.createNativeQuery("select secret from customer where id = ?");
        query.setParameter(1, customerId);
        return (byte[]) query.getSingleResult();
    }

    private String decrypt(byte[] encryptedInByte) {
        Query decryptQuery = em.createNativeQuery("select pgp_pub_decrypt(?, dearmor(current_setting('keys.private')), current_setting('decryption.password'))");
        decryptQuery.setParameter(1, encryptedInByte);
        return (String) decryptQuery.getSingleResult();
    }
}