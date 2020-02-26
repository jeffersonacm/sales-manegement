package br.com.jefferson.salesmanegement.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jefferson.salesmanegement.domain.models.Group;
import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.domain.repository.GroupRepository;
import br.com.jefferson.salesmanegement.exceptions.UserNotFoundException;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

}