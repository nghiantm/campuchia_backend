package nghia.campuchia_backend.dto;

public class ProfileDTO {
    private String username;
    private String name;
    private String email;

    public ProfileDTO(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
