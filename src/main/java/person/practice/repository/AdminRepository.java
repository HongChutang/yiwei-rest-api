package person.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import person.practice.model.Admin;

/**
 * Created by Evan Hung on 2016/12/26.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
