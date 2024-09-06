package nghia.campuchia_backend.service;

import nghia.campuchia_backend.dto.group.CreateGroupRequestDTO;
import nghia.campuchia_backend.model.Group;
import nghia.campuchia_backend.repository.GroupMemberRepository;
import nghia.campuchia_backend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public List<Group> getGroupsByUserId(String userId) {
        // Get all group IDs where the user is a member
        List<String> groupIds = groupMemberRepository.findGroupIdsByUserId(userId);

        // Fetch and return the group details
        return groupRepository.findAllById(groupIds);
    }

}
