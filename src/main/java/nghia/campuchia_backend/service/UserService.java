package nghia.campuchia_backend.service;

import nghia.campuchia_backend.dto.ProfileWithTokensDTO;
import nghia.campuchia_backend.exception.InvalidCredentialsException;
import nghia.campuchia_backend.exception.UserAlreadyExistsException;
import nghia.campuchia_backend.model.User;
import nghia.campuchia_backend.repository.UserRepository;
import nghia.campuchia_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User getUserById(String user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    public User updateName(String user_id, String new_name) {
        Optional<User> existingUserOpt = userRepository.findById(user_id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            // Update name field
            existingUser.setName(new_name);
            return userRepository.save(existingUser);
        } else {
            return null; // User not found
        }
    }

    public User validateUserCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }
}
