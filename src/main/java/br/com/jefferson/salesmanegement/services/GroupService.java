package br.com.jefferson.salesmanegement.services;

import java.util.List;
import java.util.Optional;

import br.com.jefferson.salesmanegement.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jefferson.salesmanegement.domain.models.Group;
import br.com.jefferson.salesmanegement.domain.repository.GroupRepository;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RequestUtil requestUtil;

    public Optional<Group> findById(Long id) {
        return groupRepository.findByIdAndUser(id, requestUtil.getUserRequest());
    }

    public List<Group> findAll() {
        return groupRepository.findAllByUser(requestUtil.getUserRequest());
    }

    public Group save(Group group) {
        group.setUser(requestUtil.getUserRequest());
        return groupRepository.save(group);
    }

}
