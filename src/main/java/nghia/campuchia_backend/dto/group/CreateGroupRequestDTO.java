package nghia.campuchia_backend.dto.group;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CreateGroupRequestDTO {

    @Schema(nullable = false)
    private final String group_name;

    @Schema(nullable = true)
    private final String description;

    @Schema(nullable = true)
    private final String group_avatar;

    public CreateGroupRequestDTO(
            String group_name,
            String description,
            String group_avatar
    ) {
        this.group_name = group_name;
        this.description = description;
        this.group_avatar = group_avatar;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getDescription() {
        return description;
    }

    public String getGroup_avatar() {
        return group_avatar;
    }
}
