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
import nghia.campuchia_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/group_member")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private JwtUtil jwtUtil;

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
    public GroupMember addGroupMember(@RequestBody GroupMember groupMember) {
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

    @GetMapping("/by_token")
    @Operation(
            summary = "Get groups of a member through accessToken.",
            description = "Get all the groups of a token back in a list."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Response contain the list of groups user is in.",
                    content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Group.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthenticated. Missing accessToken, or expired accessToken.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Missing or expired accessToken.\"}"))
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
    public ResponseEntity<List<GroupMember>> getMembersByToken(
            @Parameter(description = "Authorization header with Bearer token", required = true)
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtil.getUsernameFromToken(token);

            if (jwtUtil.validateToken(token, username)) {
                List<GroupMember> members = groupMemberService.findMyUserId(username);
                return new ResponseEntity<>(members, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
