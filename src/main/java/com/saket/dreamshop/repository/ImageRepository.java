package com.saket.dreamshop.repository;


import com.saket.dreamshop.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {


    List<Image> findByProductId(String id);

}

