package person.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import person.practice.model.GoodsType;

import java.util.List;

/**
 * Created by Evan Hung on 2016/12/27.
 */
public interface GoodsTypeRepository extends JpaRepository<GoodsType, String> {
}
