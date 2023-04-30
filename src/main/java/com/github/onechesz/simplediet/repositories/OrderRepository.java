package com.github.onechesz.simplediet.repositories;

import com.github.onechesz.simplediet.entities.OrderEntity;
import com.github.onechesz.simplediet.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByUserEntity(UserEntity userEntity);
}
