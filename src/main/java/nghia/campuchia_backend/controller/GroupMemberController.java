package nghia.campuchia_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nghia.campuchia_backend.model.Group;
import nghia.campuchia_backend.model.GroupMember;
import nghia.campuchia_backend.model.User;
import nghia.campuchia_backend.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group_member")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @PostMapping
    @Operation(
            summary = "Add member to a group.",
            description = "Create a record of a member being in a group. ID IS NOT NEEDED"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Group member added successfuly. Response contain the created group member object",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = GroupMember.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthenticated. Missing idToken, or expired idToken.",
                    content = {
                            @Content(mediaType = "none")
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server issue. Example: syntax issue, duplicate field in MySQL.",
                    content = {
                            @Content(mediaType = "none")
                    }
            )
    })
    public GroupMember addGroupMember(
            @Parameter(
                    description = "Must include `group_id`, `user_id`, `add_date`, and 'added_by_user_id'. 'id' is not needed.",
                    required = true,
                    schema = @Schema(implementation = GroupMember.class)
            )
            @RequestBody GroupMember groupMember) {
        return groupMemberService.saveGroupMember(groupMember);
    }

    @GetMapping("/by_group")
    @Operation(
            summary = "Get members of a group.",
            description = "Get all the members of a group back in a list."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Group exist. Response contain the list of group members.",
                    content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GroupMember.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthenticated. Missing idToken, or expired idToken.",
                    content = {
                            @Content(mediaType = "none")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Group not found",
                    content = {
                            @Content(mediaType = "none")
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server issue. Example: syntax issue, duplicate field in MySQL.",
                    content = {
                            @Content(mediaType = "none")
                    }
            )
    })
    public List<GroupMember> getMembersByGroup(
            @Parameter(
                    description = "Must include `group_id` as parameter.",
                    required = true
            )
            @RequestParam("group_id") String group_id) {
        return groupMemberService.findByGroupId(group_id);
    }

    @GetMapping("/by_id")
    @Operation(
            summary = "Get groups of a member.",
            description = "Get all the groups of a member back in a list."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User exist. Response contain the list of groups user is in.",
                    content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Group.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthenticated. Missing idToken, or expired idToken.",
                    content = {
                            @Content(mediaType = "none")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = {
                            @Content(mediaType = "none")
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server issue. Example: syntax issue, duplicate field in MySQL.",
                    content = {
                            @Content(mediaType = "none")
                    }
            )
    })
    public List<GroupMember> getMembersByUser(
            @Parameter(
                    description = "Must include `user_id` as parameter.",
                    required = true
            )
            @RequestParam("user_id") String user_id) {
        return groupMemberService.findMyUserId(user_id);
    }
}
