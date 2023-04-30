package com.github.onechesz.simplediet.repositories;

import com.github.onechesz.simplediet.embeddable.OrdersProductsKey;
import com.github.onechesz.simplediet.entities.OrdersProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersProductsRepository extends JpaRepository<OrdersProductsEntity, OrdersProductsKey> {

}
