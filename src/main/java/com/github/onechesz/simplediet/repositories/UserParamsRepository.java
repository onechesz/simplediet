package com.github.onechesz.simplediet.repositories;

import com.github.onechesz.simplediet.entities.UserParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserParamsRepository extends JpaRepository<UserParams, Integer> {

}
