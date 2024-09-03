package nghia.campuchia_backend.dto;

public class SignInCredentialDTO {
    private String email;
    private String password;

    public SignInCredentialDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
