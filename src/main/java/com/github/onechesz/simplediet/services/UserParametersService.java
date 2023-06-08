package com.github.onechesz.simplediet.services;

import com.github.onechesz.simplediet.entities.UserParametersEntity;
import com.github.onechesz.simplediet.repositories.UserParamsRepository;
import com.github.onechesz.simplediet.repositories.UserRepository;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserParametersService {
    private final UserParamsRepository userParamsRepository;
    private final UserRepository userRepository;

    @Contract(pure = true)
    public UserParametersService(UserParamsRepository userParamsRepository, UserRepository userRepository) {
        this.userParamsRepository = userParamsRepository;
        this.userRepository = userRepository;
    }

    public UserParametersEntity findById(int id) {
        return userParamsRepository.findById(id).orElse(new UserParametersEntity("male", 0, 0, 0));
    }

    @Transactional
    public void save(int id, UserParametersEntity newUserParametersEntity) {
        userParamsRepository.findById(id).ifPresentOrElse(userParametersEntity -> {
            userParametersEntity.setSex(newUserParametersEntity.getSex());
            userParametersEntity.setAge(newUserParametersEntity.getAge());
            userParametersEntity.setHeight(newUserParametersEntity.getHeight());
            userParametersEntity.setWeight(newUserParametersEntity.getWeight());
            userParamsRepository.save(userParametersEntity);
        }, () -> {
            userRepository.findById(id).ifPresent(userEntity -> {
                newUserParametersEntity.setUserEntity(userEntity);
                userParamsRepository.save(newUserParametersEntity);
            });
        });
    }
}
