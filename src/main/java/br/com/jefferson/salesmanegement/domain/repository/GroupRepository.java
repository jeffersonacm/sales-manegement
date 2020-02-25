package br.com.jefferson.salesmanegement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jefferson.salesmanegement.domain.models.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
