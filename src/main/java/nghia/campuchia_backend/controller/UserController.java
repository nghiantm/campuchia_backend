package nghia.campuchia_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nghia.campuchia_backend.model.User;
import nghia.campuchia_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    @Operation(
            summary = "Create a new user",
            description = "Create a new user based on information provided by frontend after successfully register with Firebase"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User created successfuly. Response contain the created user object",
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
                    responseCode = "500",
                    description = "Internal server issue. Example: syntax issue, duplicate field in MySQL.",
                    content = {
                            @Content(mediaType = "none")
                    }
            )
    })
    public User createUser(
            @Parameter(
                    description = "Must include `name`, `email`, and `user_id`.",
                    required = true,
                    schema = @Schema(implementation = User.class)
            )
            @RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/api/user")
    @Operation(
            summary = "Get an user by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found successfully",
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
    public ResponseEntity<User> getUserById(
            @Parameter(
                    description = "Must include `user_id` as parameter.",
                    required = true
            )
            @RequestParam("user_id") String user_id) {
        User user = userService.getUserById(user_id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
