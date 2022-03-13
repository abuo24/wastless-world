package uz.wastlessworld.app.repository;// Author - Orifjon Yunusjonov
// t.me/coderr24

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.wastlessworld.app.entity.Attachment;

import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

//    List<Users> findAllByGroupId(Long groupId);
    Attachment findByHashId(String hashId);

    @Query(value = "select * from file f where f.hash_id=?1", nativeQuery = true)
    Attachment getFileByHashId(String hashId);

    @Query(value = "select a.hash_id from attachment a where a.product_id=?1 limit 1", nativeQuery = true)
    String getByProductId(Long productId);

}
