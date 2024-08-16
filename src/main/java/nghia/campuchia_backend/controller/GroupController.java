package nghia.campuchia_backend.controller;

import nghia.campuchia_backend.model.Group;
import nghia.campuchia_backend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.saveGroup(group);
    }

    @GetMapping
    public ResponseEntity<Group> getGroup(@RequestParam("group_id") String group_id) {
        Group group = groupService.findById(group_id).orElse(null);
        if (group == null) {
            return new ResponseEntity<>(group, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupService.findAll();
    }
}
