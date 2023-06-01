package com.github.onechesz.simplediet.services;

import com.github.onechesz.simplediet.dto.dtio.ProductDTIO;
import com.github.onechesz.simplediet.dto.dtoo.ProductDTOO;
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

    public List<ProductDTOO> findAll(String search, String from, String to, String sort) {
        List<ProductDTOO> productDTOOS = new ArrayList<>();

        if (search != null) {
            if (from != null) {
                if (to != null) {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationFromTo(search, from, to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationFromTo(search, from, to))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    }
                } else {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationFrom(search, from, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationFrom(search, from))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    }
                }
            } else {
                if (to != null) {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationTo(search, to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByTitleContainsWhereDurationTo(search, to))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    }
                } else {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByTitleContains(search, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByTitleContains(search))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    }
                }
            }
        } else {
            if (from != null) {
                if (to != null) {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByDurationBetween(from, to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByDurationBetween(from, to))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    }
                } else {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByDurationGreaterThan(from, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByDurationGreaterThan(from))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    }
                }
            } else {
                if (to != null) {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findByDurationLessThan(to, Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findByDurationLessThan(to))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    }
                } else {
                    if (sort != null) {
                        for (DietEntity dietEntity : dietRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, sort)))
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    } else {
                        for (DietEntity dietEntity : dietRepository.findAll())
                            productDTOOS.add(DietEntity.convertToProductDTOO(dietEntity));
                    }
                }
            }
        }

        return productDTOOS;
    }

    @Transactional
    public void save(@NotNull ProductDTIO productDTIO) throws IOException {
        MultipartFile multipartFile = productDTIO.getMultipartFile();

        multipartFile.transferTo(new File(ProductDTIO.PRODUCTS_IMAGES_PATH + multipartFile.getOriginalFilename()));
        dietRepository.save(ProductDTIO.convertToProductEntity(productDTIO));
    }

    public ProductDTOO findById(int id) {
        Optional<DietEntity> productEntityOptional = dietRepository.findById(id);

        return productEntityOptional.map(DietEntity::convertToProductDTOO).orElse(null);

    }

    @Transactional
    public void edit(int id, @NotNull ProductDTIO productDTIO) throws IOException {
        DietEntity dietEntity = dietRepository.findById(id).orElseThrow();
        File oldFile = new File(dietEntity.getFilePath());
        MultipartFile multipartFile = productDTIO.getMultipartFile();
        File newFile = new File(ProductDTIO.PRODUCTS_IMAGES_PATH + multipartFile.getOriginalFilename());

        if (!multipartFile.isEmpty()) {
            multipartFile.transferTo(newFile);
            dietRepository.save(ProductDTIO.convertToProductEntity(productDTIO, id));

            if (!oldFile.getPath().equals(newFile.getPath()))
                if (!oldFile.delete()) throw new IOException();
        } else {
            dietEntity.setTitle(productDTIO.getTitle());
            dietEntity.setDuration(productDTIO.getPrice());
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
