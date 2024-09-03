package nghia.campuchia_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nghia.campuchia_backend.dto.SignInCredentialDTO;
import nghia.campuchia_backend.dto.ProfileWithTokensDTO;
import nghia.campuchia_backend.model.User;
import nghia.campuchia_backend.service.UserService;
import nghia.campuchia_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

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
