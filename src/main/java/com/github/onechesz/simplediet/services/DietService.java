package com.github.onechesz.simplediet.services;

import com.github.onechesz.simplediet.dto.dtio.DietDTIO;
import com.github.onechesz.simplediet.dto.dtoo.DietDTOO;
import com.github.onechesz.simplediet.entities.DietEntity;
import com.github.onechesz.simplediet.repositories.DietRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DietService {
    private final DietRepository dietRepository;

    @Contract(pure = true)
    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    public List<DietDTOO> findAll(String search, String from, String to, String sort) {
        List<DietDTOO> dietDTOOS = new ArrayList<>();

        if (search != null) {
            if (from != null) {
                if (to != null) {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationFromTo(search, from, to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationFromTo(search, from, to))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    }
                } else {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationFrom(search, from, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationFrom(search, from))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    }
                }
            } else {
                if (to != null) {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationTo(search, to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationTo(search, to))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    }
                } else {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByTitleContains(search, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByTitleContains(search))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    }
                }
            }
        } else {
            if (from != null) {
                if (to != null) {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByDurationBetween(from, to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByDurationBetween(from, to))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    }
                } else {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByDurationGreaterThan(from, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByDurationGreaterThan(from))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    }
                }
            } else {
                if (to != null) {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByDurationLessThan(to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByDurationLessThan(to))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    }
                } else {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findAll())
                            dietDTOOS.add(DietEntity.convertToDietDTOO(dietEntity));
                    }
                }
            }
        }

        return dietDTOOS;
    }

    @Transactional
    public void save(@NotNull DietDTIO dietDTIO) throws IOException {
        MultipartFile multipartFile = dietDTIO.getMultipartFile();

        multipartFile.transferTo(new File(DietDTIO.UPLOADS_IMAGES_PATH + multipartFile.getOriginalFilename()));
        dietRepository.save(DietDTIO.convertToDietEntity(dietDTIO));
    }

    public DietDTOO findById(int id) {
        Optional<DietEntity> dietEntityOptional = dietRepository.findById(id);

        return dietEntityOptional.map(DietEntity::convertToDietDTOO).orElse(null);

    }

    @Transactional
    public void edit(int id, @NotNull DietDTIO dietDTIO) throws IOException {
        DietEntity dietEntity = dietRepository.findById(id).orElseThrow();
        File oldFile = new File(dietEntity.getFilePath());
        MultipartFile multipartFile = dietDTIO.getMultipartFile();
        File newFile = new File(DietDTIO.UPLOADS_IMAGES_PATH + multipartFile.getOriginalFilename());

        if (!multipartFile.isEmpty()) {
            multipartFile.transferTo(newFile);
            dietRepository.save(DietDTIO.convertToDietEntity(dietDTIO, id));

            if (!oldFile.getPath().equals(newFile.getPath()))
                if (!oldFile.delete()) throw new IOException();
        } else {
            dietEntity.setTitle(dietDTIO.getTitle());
            dietEntity.setDuration(dietDTIO.getDuration());
            dietRepository.save(dietEntity);
        }
    }

    @Transactional
    public void deleteById(int id) throws IOException {
        DietEntity dietEntity = dietRepository.findById(id).orElse(null);

        if (dietEntity != null) {
            File file = new File(dietEntity.getFilePath());

            if (!file.delete()) throw new IOException();
        }

        dietRepository.deleteById(id);
    }
}
