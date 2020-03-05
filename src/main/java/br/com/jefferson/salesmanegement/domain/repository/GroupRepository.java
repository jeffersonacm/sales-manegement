package br.com.jefferson.salesmanegement.domain.repository;

import br.com.jefferson.salesmanegement.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jefferson.salesmanegement.domain.models.Group;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByUser(User user);

    Optional<Group> findByIdAndUser(Long id, User user);

}
