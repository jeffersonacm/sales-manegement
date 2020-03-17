package br.com.jefferson.salesmanegement.services;

import java.util.List;
import java.util.Optional;

import br.com.jefferson.salesmanegement.exceptions.InvalidArgumentException;
import br.com.jefferson.salesmanegement.utils.RequestUtil;
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

    @Autowired
    private RequestUtil requestUtil;

    public Optional<Sale> findById(Long id) {
        return saleRepository.findByIdAndUser(id, requestUtil.getUserRequest());
    }

    public List<Sale> findAll() {
        return saleRepository.findAllByUser(requestUtil.getUserRequest());
    }

    public Sale save(Sale sale) {
        Long idGroup = sale.getGroup().getId();
        Optional<Group> group = groupService.findById(idGroup);

        if (group.isPresent()) {
            sale.setUser(requestUtil.getUserRequest());
            sale.setGroup(group.get());

            return saleRepository.save(sale);

        } else {
            throw new InvalidArgumentException("Não foi possível encontrar um grupo com o id: " + idGroup);
        }
    }

    public Boolean isSaleExist(Long id) {
        return this.findById(id).isPresent();
    }

}
