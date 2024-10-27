package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Tutorial;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class CustomTutorialRepositoryImpl implements CustomTutorialRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void saveTutorials(List<Tutorial> tutorials) {
        List<String> paramList = new ArrayList<>();
        String sentence = "insert into Tutorial (categoryId, description, published, title) values ";
        // int totalChunk = 5;

        tutorials.forEach(item -> {
            String id = item.getCategoryId().toString();

            paramList.add("(:categoryId_" + id + ", :description_" + id + ", :published_" + id + ", :title_" + id + ")");
        });

        String params = String.join(", ", paramList);
        Query query = entityManager.createQuery(sentence + params);

        tutorials.forEach(item -> {
            String id = item.getCategoryId().toString();

            query.setParameter("categoryId_" + id, item.getCategoryId());
            query.setParameter("published_" + id, item.getPublished());
            query.setParameter("title_" + id, item.getTitle());
            query.setParameter("description_" + id, item.getDescription());
                    
            // if (i % totalChunk == 0)
            // flush a batch of inserts and release memory
        });

        // flush a batch of inserts and release memory
        query.executeUpdate();
        entityManager.flush();
        entityManager.clear();
    }

}
