package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import wolox.training.models.User;

import java.time.LocalDate;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    @Query(value = "select u from User u where ((u.birthDate > :start and u.birthDate < :end) or u.birthDate is null)" +
                    "AND (u.name = :name or u.name is null)")
    Page<User> findByBirthDateBetweenAndNameContainingAllIgnoreCase(LocalDate start, LocalDate end, String name,
                                                                    Pageable pageable);

    @Query(value = "select u from User u where u.birthDate is null or ((u.birthDate > :start and u.birthDate < :end)" +
            "AND (u.name like %:name%)")
    Page<User> findAll(LocalDate start, LocalDate end, String name, Pageable pageable);
}
