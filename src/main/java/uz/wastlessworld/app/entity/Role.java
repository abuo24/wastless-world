package uz.wastlessworld.app.entity;// Author - Orifjon Yunusjonov
// t.me/coderr24


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(strategy = "uuid",name = "uuid")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
