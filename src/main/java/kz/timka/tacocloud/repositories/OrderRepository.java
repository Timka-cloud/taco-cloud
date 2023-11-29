package kz.timka.tacocloud.repositories;

import kz.timka.tacocloud.data.TacoOrder;
import kz.timka.tacocloud.data.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Order;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc (User user, Pageable pageable);

}
