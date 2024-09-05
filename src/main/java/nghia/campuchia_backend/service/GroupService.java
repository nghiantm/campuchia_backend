package nghia.campuchia_backend.service;

import nghia.campuchia_backend.dto.group.CreateGroupRequestDTO;
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

    public Group saveGroup(CreateGroupRequestDTO requestDTO) {
        Group group = new Group();
        group.setGroup_name(requestDTO.getGroup_name());
        group.setDescription(requestDTO.getDescription());
        group.setGroup_avatar(requestDTO.getGroup_avatar());
        group.setCreated_by(requestDTO.getCreated_by());
        return groupRepository.save(group);
    }

    public Optional<Group> findById(String groupId) {
        return groupRepository.findById(groupId);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

}
