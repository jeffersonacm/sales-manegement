package br.com.jefferson.salesmanegement.services;

import br.com.jefferson.salesmanegement.domain.models.Categorie;
import br.com.jefferson.salesmanegement.domain.repository.CategoryRepository;
import br.com.jefferson.salesmanegement.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RequestUtil requestUtil;

    public Optional<Categorie> findById(Long id) {
        return categoryRepository.findByIdAndUser(id, requestUtil.getUserRequest());
    }

    public List<Categorie> findAll() {
        return categoryRepository.findAllByUser(requestUtil.getUserRequest());
    }

    public Categorie save(Categorie categorie) {
        categorie.setUser(requestUtil.getUserRequest());
        return categoryRepository.save(categorie);
    }

}
