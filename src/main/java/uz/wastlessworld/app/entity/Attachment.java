package uz.wastlessworld.app.entity;// Author - Orifjon Yunusjonov
// t.me/coderr24

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment implements Serializable {
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(strategy = "uuid",name = "uuid")
//    private UUID id;

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    protected UUID id;


    private String uploadPath;

    private String hashId;

    private String name;

    private Long fileSize;

    private String contentType;

    private String extension;


}
