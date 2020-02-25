package br.com.jefferson.salesmanegement.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jefferson.salesmanegement.domain.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMail(String mail);

}
