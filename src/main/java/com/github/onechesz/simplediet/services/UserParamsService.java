package com.github.onechesz.simplediet.services;

import com.github.onechesz.simplediet.entities.UserParams;
import com.github.onechesz.simplediet.repositories.UserParamsRepository;
import com.github.onechesz.simplediet.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserParamsService {
    private final UserParamsRepository userParamsRepository;
    private final UserRepository userRepository;

    public UserParamsService(UserParamsRepository userParamsRepository, UserRepository userRepository) {
        this.userParamsRepository = userParamsRepository;
        this.userRepository = userRepository;
    }

    public UserParams findById(int id) {
        return userParamsRepository.findById(id).orElse(new UserParams("male", 0, 0, 0));
    }

    @Transactional
    public void save(int id, UserParams newUserParams) {
        userParamsRepository.findById(id).ifPresentOrElse(userParams -> {
            userParams.setSex(newUserParams.getSex());
            userParams.setAge(newUserParams.getAge());
            userParams.setHeight(newUserParams.getHeight());
            userParams.setWeight(newUserParams.getWeight());
            userParamsRepository.save(userParams);
        }, () -> {
            userRepository.findById(id).ifPresent(userEntity -> {
                newUserParams.setUserEntity(userEntity);
                userParamsRepository.save(newUserParams);
            });
        });
    }
}
