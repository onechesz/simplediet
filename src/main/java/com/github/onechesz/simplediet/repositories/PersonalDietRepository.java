package com.github.onechesz.simplediet.repositories;

import com.github.onechesz.simplediet.entities.PersonalDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDietRepository extends JpaRepository<PersonalDietEntity, Integer> {

}
