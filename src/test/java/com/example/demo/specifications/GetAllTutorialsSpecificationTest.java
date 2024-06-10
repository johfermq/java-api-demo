package com.example.demo.specifications;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.model.Category;
import com.example.demo.model.Tutorial;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
class GetAllTutorialsSpecificationTest {

    @MockBean
    private CriteriaBuilder criteriaBuilder;

    @MockBean
    private CriteriaQuery<?> criteriaQuery;

    @MockBean
    private Root<Tutorial> tutorialRoot;

    @Mock(extraInterfaces = { Join.class })
    private Fetch<Tutorial, Category> fetch;

    @Test
    void testToPredicateNoSearch() {
        Specification<Tutorial> specification = new GetAllTutorialsSpecification("");
        specification.toPredicate(this.tutorialRoot, this.criteriaQuery, this.criteriaBuilder);

        verify(this.tutorialRoot, times(1)).fetch("category", JoinType.LEFT);
        verify(this.tutorialRoot, times(1)).get("title");
        verify(this.tutorialRoot, times(0)).get("description");

        Path<String> titlePath = this.tutorialRoot.get("title");
        verify(this.criteriaBuilder, times(1)).asc(titlePath);
        verify(this.criteriaBuilder, times(1)).conjunction();
        verify(this.criteriaBuilder, times(0)).or();

        verify(this.criteriaQuery, times(1)).orderBy(this.criteriaBuilder.asc(titlePath));
    }

    @Test
    void testToPredicateSearch() {
        String search = "test".toLowerCase();
        String searchLike = "%".concat(search).concat("%");
        List<Predicate> predicates = new ArrayList<>();

        Specification<Tutorial> specification = new GetAllTutorialsSpecification(search);

        when(this.tutorialRoot.<Tutorial, Category>fetch("category", JoinType.LEFT)).thenReturn(this.fetch);

        specification.toPredicate(this.tutorialRoot, this.criteriaQuery, this.criteriaBuilder);

        /* Optional<Fetch<Tutorial, ?>> fetchCategory = this.tutorialRoot.getFetches().stream()
                .filter(el -> el.getAttribute().getName().equals("category")).findFirst();
        Join<?, ?> joinCategory = (Join<?, ?>) fetchCategory.get(); */

        verify(this.tutorialRoot, times(1)).fetch("category", JoinType.LEFT);
        verify(this.tutorialRoot, times(2)).get("title");
        verify(this.tutorialRoot, times(1)).get("description");

        Path<String> testPath = this.tutorialRoot.get("test");
        verify(this.criteriaBuilder, times(3)).lower(testPath);

        Expression<String> testExpression = this.criteriaBuilder.lower(testPath);
        verify(this.criteriaBuilder, times(3)).like(testExpression, searchLike);

        verify(this.criteriaBuilder, times(1)).asc(this.tutorialRoot.get("title"));
        verify(this.criteriaBuilder, times(0)).conjunction();
        verify(this.criteriaBuilder, times(1)).or(predicates.toArray(new Predicate[3]));
        verify(this.criteriaQuery, times(1)).orderBy(this.criteriaBuilder.asc(this.tutorialRoot.get("title")));
    }
}
