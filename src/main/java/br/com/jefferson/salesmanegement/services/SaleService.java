package br.com.jefferson.salesmanegement.services;

import java.util.List;
import java.util.Optional;

import br.com.jefferson.salesmanegement.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jefferson.salesmanegement.domain.models.Group;
import br.com.jefferson.salesmanegement.domain.models.Sale;
import br.com.jefferson.salesmanegement.domain.repository.SaleRepository;

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
        Long idGroup = sale.getGroup().getId();
        Optional<Group> group = groupService.findById(idGroup);

        if (group.isPresent()) {
            sale.setGroup(group.get());
            return saleRepository.save(sale);

        } else {
            throw new ResourceNotFoundException("Não foi possível encontrar um grupo com o id: " + idGroup);
        }
    }

}
