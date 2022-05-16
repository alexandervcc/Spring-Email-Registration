package acc.spring.secemail.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import acc.spring.secemail.Model.UserApp;

@Repository
@Transactional(readOnly = true)
public interface IUserRepository extends JpaRepository<UserApp,Long>{
    Optional<UserApp> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserApp a SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
