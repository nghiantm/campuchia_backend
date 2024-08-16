package nghia.campuchia_backend.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "tbl_group_members")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Primary key for JPA, not required in original design

    @Column(name = "group_id", nullable = false)
    private String groupId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "add_date", nullable = false)
    private Date addDate;

    @Column(name = "added_by_user_id", nullable = true)
    private String addedByUserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String group_id) {
        this.groupId = group_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user_id) {
        this.userId = user_id;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date add_date) {
        this.addDate = add_date;
    }

    public String getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(String added_by_user_id) {
        this.addedByUserId = added_by_user_id;
    }
}
