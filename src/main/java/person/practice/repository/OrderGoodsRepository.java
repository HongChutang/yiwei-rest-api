package person.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import person.practice.model.OrderGoods;

import java.util.List;

/**
 * Created by Evan Hung on 2016/12/26.
 */
public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long>{
    List<OrderGoods> findByUserEmail(String userEmail);
}
