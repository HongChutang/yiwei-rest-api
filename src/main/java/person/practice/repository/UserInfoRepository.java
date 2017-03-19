package person.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import person.practice.model.UserInfo;

/**
 * Created by Evan Hung on 2016/12/14.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByEmail(String email);
}
