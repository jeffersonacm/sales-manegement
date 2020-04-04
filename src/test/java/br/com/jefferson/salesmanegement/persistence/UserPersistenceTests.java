package br.com.jefferson.salesmanegement.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.domain.repository.UserRepository;

@SpringBootTest
class UserPersistenceTests {

    @Autowired
    UserRepository UserRepository;

    @Test
    void SaveUserTest() {

        User user = new User();
        user.setName("jefferson");
        user.setMail("jefferson@hotmail.com");
        user.setPassword("123456");

        User userSaved = UserRepository.save(user);

        Optional<User> userFound = UserRepository.findById(userSaved.getId());

        assertNotNull(userFound);
        assertEquals(userSaved.getId(), userFound.get().getId());
    }

    // @Test
    void SaveUserUniqueEmailTest() {
        boolean insert = false;

        try {
            User user = new User();
            user.setName("jefferson");
            user.setMail("jefferson123@hotmail.com");
            user.setPassword("123456");
            UserRepository.save(user);
            UserRepository.save(user);

            insert = true;
        } catch (Exception e) {
        }

        assertEquals(false, insert);
    }

    @Test
    void SaveUserWithoutData() {
        boolean insert = false;

        try {
            User user = new User();
            user.setName(null);
            user.setMail(null);
            user.setPassword(null);
            UserRepository.save(user);

            insert = true;
        } catch (Exception e) {
            insert = false;
        }

        assertEquals(false, insert);
    }
}