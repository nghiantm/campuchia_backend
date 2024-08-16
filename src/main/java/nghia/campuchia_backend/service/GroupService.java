package nghia.campuchia_backend.service;

import nghia.campuchia_backend.model.Group;
import nghia.campuchia_backend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public Optional<Group> findById(String groupId) {
        return groupRepository.findById(groupId);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

}
