package com.github.onechesz.simplediet.repositories;

import com.github.onechesz.simplediet.entities.UserParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserParametersRepository extends JpaRepository<UserParametersEntity, Integer> {

}
