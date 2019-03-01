package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query(value = "select u from User u where ((u.birthDate > :start and u.birthDate < :end) or u.birthDate is null)" +
                    "AND (u.name = :name or u.name is null)")
    List<User> findByBirthDateBetweenAndNameContainingAllIgnoreCase(LocalDate start, LocalDate end, String name);

    @Query(value = "select u from User u where ((u.birthDate > :start and u.birthDate < :end) or u.birthDate is null" +
            "AND (u.name = :name or u.name is null)")
    Iterable findAll(LocalDate start, LocalDate end, String name);
}
