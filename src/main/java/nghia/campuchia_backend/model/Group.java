package nghia.campuchia_backend.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "tbl_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_id", nullable = false, unique = true, length = 36)
    private String group_id;

    @Column(name = "group_name", nullable = false)
    private String group_name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "group_avatar", nullable = true)
    private String group_avatar;

    @Column(name = "creation_date", nullable = false)
    private Date creation_date;

    @Column(name = "created_by", nullable = false)
    private String created_by;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup_avatar() {
        return group_avatar;
    }

    public void setGroup_avatar(String group_avatar) {
        this.group_avatar = group_avatar;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
