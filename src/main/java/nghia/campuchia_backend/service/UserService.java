package nghia.campuchia_backend.service;

import nghia.campuchia_backend.model.User;
import nghia.campuchia_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /*
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }*/

    public User getUserById(String user_id) {
        return userRepository.findById(user_id).orElse(null);
    }
}
