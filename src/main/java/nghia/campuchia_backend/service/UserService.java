package nghia.campuchia_backend.service;

import nghia.campuchia_backend.model.User;
import nghia.campuchia_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
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
}
