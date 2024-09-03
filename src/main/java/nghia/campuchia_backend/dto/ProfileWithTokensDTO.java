package nghia.campuchia_backend.dto;

public class ProfileWithTokensDTO {
    private String username;
    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;

    public ProfileWithTokensDTO(String username, String displayName, String email, String accessToken, String refreshToken) {
        this.username = username;
        this.name = displayName;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
