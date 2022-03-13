package uz.wastlessworld.app.repository;// Author - Orifjon Yunusjonov
// t.me/coderr24

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.wastlessworld.app.entity.helpers.ConfirmCode;

import java.util.List;

@Repository
public interface ConfirmCodeRepository extends JpaRepository<ConfirmCode, Long> {

    List<ConfirmCode> findAllByUser_UsernameOrderByDateDesc(String username);

}
