package br.com.jefferson.salesmanegement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jefferson.salesmanegement.domain.models.Group;
import br.com.jefferson.salesmanegement.domain.models.Sale;
import br.com.jefferson.salesmanegement.domain.repository.SaleRepository;
import br.com.jefferson.salesmanegement.exceptions.GroupNotFoundException;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private GroupService groupService;

    public Optional<Sale> findSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Sale save(Sale sale) {
        Optional<Group> group = groupService.findById(sale.getGroup().getId());

        if (group.isPresent()) {
            sale.setGroup(group.get());
            return saleRepository.save(sale);

        } else {
            throw new GroupNotFoundException();
        }
    }

}
