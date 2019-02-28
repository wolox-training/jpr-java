package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query(value = "select u from User u where u.birthDate > :start and u.birthDate < :end")
    List<User> findByNameContainingAllIgnoreCaseAndNameIsNull(@Param("start") LocalDate start,
                                                                    @Param("end") LocalDate end);
}
