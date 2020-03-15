package br.com.jefferson.salesmanegement.domain.repository;

import br.com.jefferson.salesmanegement.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jefferson.salesmanegement.domain.models.Sale;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findAllByUser(User user);

    Optional<Sale> findByIdAndUser(Long id, User user);

}
