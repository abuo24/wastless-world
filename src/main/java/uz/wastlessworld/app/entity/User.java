package uz.wastlessworld.app.entity;// Author - Orifjon Yunusjonov
// t.me/coderr24

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    protected UUID id;

    //    phoneNumber
    @Column(unique = true)
    @Pattern(regexp = "^998(9[0-9]|6[125679]|7[125679]|8[8]|3[3])[0-9]{7}$")
    private String username;

    @Column(columnDefinition = "TEXT")
    private String fullname;

    private String password;

    //    hashId
    private String avatarUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public User(String username, String fullname) {
        this.username = username;
        this.fullname = fullname;
    }

    public User(String username, String fullname, List<Role> roles) {
        this.username = username;
        this.fullname = fullname;
        this.roles = roles;
    }
}
