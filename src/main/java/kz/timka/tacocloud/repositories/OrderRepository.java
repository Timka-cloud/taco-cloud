package kz.timka.tacocloud.repositories;

import kz.timka.tacocloud.data.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder tacoOrder);
}
