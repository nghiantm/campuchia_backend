package nghia.campuchia_backend.repository;

import nghia.campuchia_backend.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    List<GroupMember> findByGroupId(String groupId);
    Optional<List<GroupMember>> findByUserId(String userId);

    @Query("SELECT gm.groupId FROM GroupMember gm WHERE gm.userId = :userId")
    List<String> findGroupIdsByUserId(String userId);
}