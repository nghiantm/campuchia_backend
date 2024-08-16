package nghia.campuchia_backend.service;

import nghia.campuchia_backend.model.GroupMember;
import nghia.campuchia_backend.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public GroupMember saveGroupMember(GroupMember groupMember) {
        return groupMemberRepository.save(groupMember);
    }

    public List<GroupMember> findByGroupId(String group_id) {
        return groupMemberRepository.findByGroupId(group_id);
    }

    public List<GroupMember> findMyUserId(String user_id) {
        return groupMemberRepository.findByUserId(user_id);
    }
}
