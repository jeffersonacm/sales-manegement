package br.com.jefferson.salesmanegement.domain.repository;

import br.com.jefferson.salesmanegement.domain.models.Categorie;
import br.com.jefferson.salesmanegement.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categorie, Long> {

    List<Categorie> findAllByUser(User user);

    Optional<Categorie> findByIdAndUser(Long id, User user);

}
