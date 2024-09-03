package nghia.campuchia_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nghia.campuchia_backend.dto.ProfileDTO;
import nghia.campuchia_backend.dto.ProfileWithTokensDTO;
import nghia.campuchia_backend.dto.SignInCredentialDTO;
import nghia.campuchia_backend.model.User;
import nghia.campuchia_backend.service.UserService;
import nghia.campuchia_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    @Operation(
            summary = "Get an user by accessToken"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDTO.class))
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
    public ResponseEntity<ProfileDTO> getUserById(
            @Parameter(description = "Authorization header with Bearer token", required = true)
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtil.getUsernameFromToken(token);

            if (jwtUtil.validateToken(token, username)) {
                User user = userService.getUserById(username);
                if (user != null) {
                    return new ResponseEntity<>(
                            new ProfileDTO(
                                    user.getUsername(),
                                    user.getName(),
                                    user.getEmail()
                            ),
                            HttpStatus.OK
                    );
                }
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
    @PutMapping("/api/user")
    @Operation(
            summary = "Update user name",
            description = "Update the name of an existing user based on their user_id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User name updated successfully. Response contains the updated user object",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
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
                    description = "User not found with the provided id",
                    content = {
                            @Content(mediaType = "none")
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server issue. Example: syntax issue.",
                    content = {
                            @Content(mediaType = "none")
                    }
            )
    })
    public ResponseEntity<User> updateUserName(
            @Parameter(
                    description = "ID of the user to update",
                    required = true
            )
            @RequestParam String user_id,
            @Parameter(
                    description = "New name of the user",
                    required = true
            )
            @RequestParam String new_name){
        User updated_user = userService.updateName(user_id, new_name);
        if (updated_user != null) {
            return new ResponseEntity<>(updated_user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } */

    @PostMapping("/signup")
    @Operation(
            summary = "Register an user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileWithTokensDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Username/email already exist",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Username/Email already exists.\"}"))
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
    public ResponseEntity<ProfileWithTokensDTO> signUp(@RequestBody User user) {
        User createdUser = userService.registerNewUser(user);
        // userService would throw 409 error if duplicate credentials (Email, username)
        return new ResponseEntity<>(
                new ProfileWithTokensDTO(
                        createdUser.getUsername(),
                        createdUser.getName(),
                        createdUser.getEmail(),
                        jwtUtil.generateAccessToken(createdUser.getUsername()),
                        jwtUtil.generateRefreshToken(createdUser.getUsername())
                ),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/signin")
    @Operation(
            summary = "Sign in an user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User signed in successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileWithTokensDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Invalid credentials.\"}"))
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
    public ResponseEntity<ProfileWithTokensDTO> signIn(@RequestBody SignInCredentialDTO credential) {
        // userService would throw 401 error if invalid credentials
        User retrievedUser = userService.validateUserCredentials(credential.getEmail(), credential.getPassword());

        return new ResponseEntity<>(
                new ProfileWithTokensDTO(
                        retrievedUser.getUsername(),
                        retrievedUser.getName(),
                        retrievedUser.getEmail(),
                        jwtUtil.generateAccessToken(retrievedUser.getUsername()),
                        jwtUtil.generateRefreshToken(retrievedUser.getUsername())
                ),
                HttpStatus.OK
        );
    }
}
