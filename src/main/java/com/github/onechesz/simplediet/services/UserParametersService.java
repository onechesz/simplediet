package com.github.onechesz.simplediet.services;

import com.github.onechesz.simplediet.dto.UserParametersDTO;
import com.github.onechesz.simplediet.entities.UserParametersEntity;
import com.github.onechesz.simplediet.repositories.UserParametersRepository;
import com.github.onechesz.simplediet.repositories.UserRepository;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
public class UserParametersService {
    private final UserParametersRepository userParametersRepository;
    private final UserRepository userRepository;

    @Contract(pure = true)
    public UserParametersService(UserParametersRepository userParametersRepository, UserRepository userRepository) {
        this.userParametersRepository = userParametersRepository;
        this.userRepository = userRepository;
    }

    public UserParametersDTO findById(int id) {
        UserParametersEntity userParametersEntity = userParametersRepository.findById(id).orElse(null);

        if (userParametersEntity != null) return UserParametersEntity.convertToUserParametersDTO(userParametersEntity);

        return new UserParametersDTO("male", 0, 0, 0, Collections.emptyList(), "low");
    }

    @Transactional
    public void save(int id, UserParametersDTO userParametersDTO) {
        userParametersRepository.findById(id).ifPresentOrElse(userParametersEntity -> {
            userParametersEntity.setSex(userParametersDTO.getSex());
            userParametersEntity.setAge(userParametersDTO.getAge());
            userParametersEntity.setHeight(userParametersDTO.getHeight());
            userParametersEntity.setWeight(userParametersDTO.getWeight());
            userParametersEntity.setAllergy(String.join(", ", userParametersDTO.getAllergy()));
            userParametersEntity.setPhysicalActivity(userParametersDTO.getPhysicalActivity());
            userParametersRepository.save(userParametersEntity);
        }, () -> {
            userRepository.findById(id).ifPresent(userEntity -> {
                UserParametersEntity newUserParametersEntity = UserParametersDTO.convertToUserParametersEntity(userParametersDTO);

                newUserParametersEntity.setUserId(id);
                newUserParametersEntity.setUserEntity(userEntity);
                userParametersRepository.save(newUserParametersEntity);
            });
        });
    }
}
