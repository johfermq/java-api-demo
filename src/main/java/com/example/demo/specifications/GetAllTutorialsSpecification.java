package com.example.demo.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import com.example.demo.model.Category;
import com.example.demo.model.Tutorial;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class GetAllTutorialsSpecification implements Specification<Tutorial> {

    private String search;

    @Override
    public Predicate toPredicate(@NonNull Root<Tutorial> root, @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        // Predicate list
        List<Predicate> predicates = new ArrayList<>();

        // Fetch category
        Join<?, ?> joinCategory = (Join<?, ?>) root.<Tutorial, Category>fetch("category", JoinType.LEFT);

        // If search has text
        if (StringUtils.hasText(this.search)) {
            String searchLower = this.search.toLowerCase();

            // By title
            Expression<String> title = criteriaBuilder.lower(root.get("title"));
            Predicate titlePredicate = criteriaBuilder.like(title, "%".concat(searchLower).concat("%"));
            predicates.add(titlePredicate);

            // By description
            Expression<String> description = criteriaBuilder.lower(root.get("description"));
            Predicate descriptionPredicate = criteriaBuilder.like(description, "%".concat(searchLower).concat("%"));
            predicates.add(descriptionPredicate);

            // By category
            Expression<String> categoryName = criteriaBuilder.lower(joinCategory.get("name"));
            Predicate categoryNamePredicate = criteriaBuilder.like(categoryName, "%".concat(searchLower).concat("%"));
            predicates.add(categoryNamePredicate);
        }

        // Order by
        query.orderBy(criteriaBuilder.asc(root.get("title")));

        // Return criteria builder
        if (predicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    }
}
