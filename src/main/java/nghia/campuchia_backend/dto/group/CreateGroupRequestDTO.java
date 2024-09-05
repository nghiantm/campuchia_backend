package nghia.campuchia_backend.dto.group;

import java.sql.Date;

public class CreateGroupRequestDTO {
    private final String group_name;
    private final String description;
    private final String group_avatar;
    private final Date creation_date;
    private final String created_by;

    public CreateGroupRequestDTO(
            String group_name,
            String description,
            String group_avatar,
            Date creation_date,
            String created_by
    ) {
        this.group_name = group_name;
        this.description = description;
        this.group_avatar = group_avatar;
        this.creation_date = creation_date;
        this.created_by = created_by;
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

    public Date getCreation_date() {
        return creation_date;
    }

    public String getCreated_by() {
        return created_by;
    }
}
