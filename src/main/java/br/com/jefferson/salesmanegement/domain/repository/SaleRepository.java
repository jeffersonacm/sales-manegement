package br.com.jefferson.salesmanegement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jefferson.salesmanegement.domain.models.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

}