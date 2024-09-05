package nghia.campuchia_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nghia.campuchia_backend.dto.group.CreateGroupRequestDTO;
import nghia.campuchia_backend.dto.group.GroupResponseDTO;
import nghia.campuchia_backend.exception.InvalidCredentialsException;
import nghia.campuchia_backend.model.Group;
import nghia.campuchia_backend.service.GroupService;
import nghia.campuchia_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @Operation(
            summary = "Create a group by an user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Group created successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthenticated. Missing, expired, or invalid accessToken.",
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
    public ResponseEntity<GroupResponseDTO> createGroup(
            @Parameter(description = "Authorization header with Bearer token", required = true)
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody CreateGroupRequestDTO group
    ) {
        // Validate the authorization header and extract the token
        String token = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Validate the token with the username
        if (!jwtUtil.validateToken(token, group.getCreated_by())) {
            throw new InvalidCredentialsException("Invalid or expired token.");
        }

        Group createdGroup = groupService.saveGroup(group);
        return new ResponseEntity<>(
                new GroupResponseDTO(
                        createdGroup.getGroup_id(),
                        createdGroup.getGroup_name(),
                        createdGroup.getDescription(),
                        createdGroup.getGroup_avatar(),
                        createdGroup.getCreation_date(),
                        createdGroup.getCreated_by()
                )
                , HttpStatus.CREATED
        );
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
