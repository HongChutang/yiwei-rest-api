package person.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import person.practice.model.CartGoods;

import java.util.List;

/**
 * Created by Evan Hung on 2016/12/26.
 */
public interface CartGoodsRepository extends JpaRepository<CartGoods, Long> {
    List<CartGoods> findByUserEmail(String userEmail);
}
