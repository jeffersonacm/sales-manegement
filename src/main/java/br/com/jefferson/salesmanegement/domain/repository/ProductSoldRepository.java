package br.com.jefferson.salesmanegement.domain.repository;

import br.com.jefferson.salesmanegement.domain.models.Group;
import br.com.jefferson.salesmanegement.domain.models.ProductSold;
import br.com.jefferson.salesmanegement.domain.models.Sale;
import br.com.jefferson.salesmanegement.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSoldRepository extends JpaRepository<ProductSold, Long> {

    Optional<ProductSold> findByIdAndUser(Long id, User user);

    List<ProductSold> findAllByUser(User user);

    @Query(value = "select * from tb_products_sold as ps " +
            "inner join tb_products p " +
                "on ps.product_id = p.id " +
            "where ps.user_id = :userId " +
                "and ps.sale_id = :saleId ", nativeQuery = true)
    List<ProductSold> findAllByUserAndGroupAndSale(@Param("userId") Long userId, @Param("saleId") Long saleId);

}
