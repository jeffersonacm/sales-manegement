package br.com.jefferson.salesmanegement.domain.repository;

import br.com.jefferson.salesmanegement.domain.models.Categorie;
import br.com.jefferson.salesmanegement.domain.models.Product;
import br.com.jefferson.salesmanegement.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndUser(Long id, User user);

    List<Product> findAllByCategorieAndUser(Categorie categorie, User user);

}
