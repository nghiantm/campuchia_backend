package nghia.campuchia_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nghia.campuchia_backend.dto.group.CreateGroupRequestDTO;
import nghia.campuchia_backend.dto.group.GroupResponseDTO;
import nghia.campuchia_backend.exception.InvalidCredentialsException;
import nghia.campuchia_backend.model.Group;
import nghia.campuchia_backend.repository.GroupMemberRepository;
import nghia.campuchia_backend.service.GroupService;
import nghia.campuchia_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
            summary = "Create a group by an user",
            description = "group_name is NEEDED, everything else is nullable"
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
            @RequestBody CreateGroupRequestDTO groupDTO
    ) {
        // Validate the authorization header and extract the token
        String token = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Validate the token with the username
        String user = jwtUtil.getUsernameFromToken(token);
        if (!jwtUtil.validateToken(token, user)) {
            throw new InvalidCredentialsException("Invalid or expired token.");
        }

        Group group = new Group();
        group.setGroup_name(groupDTO.getGroup_name());
        group.setDescription(groupDTO.getDescription());
        group.setGroup_avatar(groupDTO.getGroup_avatar());
        group.setCreated_by(user);
        group.setCreation_date(new Date(System.currentTimeMillis()));

        Group createdGroup = groupService.saveGroup(group);
        return new ResponseEntity<>(
                new GroupResponseDTO(
                        createdGroup.getGroup_id(),
                        createdGroup.getGroup_name(),
                        createdGroup.getDescription(),
                        createdGroup.getGroup_avatar(),
                        createdGroup.getCreation_date(),
                        user
                )
                , HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all groups of a certain user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Groups retrieved successfully",
                    content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Group.class)))
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
    public ResponseEntity<List<Group>> getGroup(
            @Parameter(description = "Authorization header with Bearer token", required = true)
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        // Validate the authorization header and extract the token
        String token = jwtUtil.extractTokenFromHeader(authorizationHeader);
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Validate the token with the username
        String user = jwtUtil.getUsernameFromToken(token);
        if (!jwtUtil.validateToken(token, user)) {
            throw new InvalidCredentialsException("Invalid or expired token.");
        }

        return new ResponseEntity<>(groupService.getGroupsByUserId(user), HttpStatus.OK);
    }
}
