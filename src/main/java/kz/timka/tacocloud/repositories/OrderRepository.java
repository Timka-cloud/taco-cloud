package kz.timka.tacocloud.repositories;

import kz.timka.tacocloud.data.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
