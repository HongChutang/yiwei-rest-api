package person.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import person.practice.model.Goods;

import java.util.List;

/**
 * Created by Evan Hung on 2016/12/26.
 */
public interface GoodsRepository extends JpaRepository<Goods, String> {
    List<Goods> findByType(String type);
}
