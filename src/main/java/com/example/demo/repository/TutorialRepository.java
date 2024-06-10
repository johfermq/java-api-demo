package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long>, JpaSpecificationExecutor<Tutorial> {

    @Query("SELECT t FROM Tutorial t LEFT JOIN FETCH t.category c WHERE t.published = :published")
    Page<Tutorial> findByPublished(boolean published, Pageable pageable);

    Page<Tutorial> findByTitleContaining(String title, Pageable pageable);

    @Query("SELECT t FROM Tutorial t LEFT JOIN FETCH t.category c WHERE t.id = :id")
    Optional<Tutorial> findByIdAndFetchCategory(long id);

}
