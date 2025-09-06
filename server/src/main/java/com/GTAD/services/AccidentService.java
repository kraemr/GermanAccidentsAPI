package com.GTAD.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.GTAD.entities.AccidentData;
import com.GTAD.repositories.AccidentDataRepository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

@Service
public class AccidentService {
    private final AccidentDataRepository accidentDataRepository;

    public AccidentService(AccidentDataRepository accidentDataRepository) {
        this.accidentDataRepository = accidentDataRepository;
    }

    public List<AccidentData> getAccidentsByYear(short year) {
        return accidentDataRepository.findByYear(year);
    }

    public Optional<AccidentData> getAccidentsById(Long id) {
        return accidentDataRepository.findById(id);
    }

    @PersistenceContext
    private EntityManager entityManager;

    public Page<AccidentData> findAccidents(
            String col,
            String cond,
            String val,
            Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<AccidentData> cq = cb.createQuery(AccidentData.class);
        Root<AccidentData> root = cq.from(AccidentData.class);

        Predicate predicate = buildPredicate(cb, root, col, cond, val);
        cq.where(predicate);

        // results
        var query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<AccidentData> results = query.getResultList();

        // count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<AccidentData> countRoot = countQuery.from(AccidentData.class);
        countQuery.select(cb.count(countRoot)).where(buildPredicate(cb, countRoot, col, cond, val));
        long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    private Predicate buildPredicate(CriteriaBuilder cb, Root<AccidentData> root,
                                     String col, String cond, String val) {
        Path<?> path = root.get(col);

        switch (cond.toLowerCase()) {
            case "eq":
                return cb.equal(path, parseValue(path, val));
            case "gt":
                if (Number.class.isAssignableFrom(path.getJavaType())) {
                    return cb.greaterThan(path.as(Integer.class), (Integer) parseValue(path, val));
                }
                return cb.greaterThan(path.as(Comparable.class), (Comparable) parseValue(path, val));

            case "lt":
                if (Number.class.isAssignableFrom(path.getJavaType())) {
                    return cb.lessThan(path.as(Integer.class), (Integer) parseValue(path, val));
                }
                return cb.lessThan(path.as(Comparable.class), (Comparable) parseValue(path, val));
            case "like":
                return cb.like(path.as(String.class), "%" + val + "%");
            default:
                throw new IllegalArgumentException("Unsupported condition: " + cond);
        }
    }

private Object parseValue(Path<?> path, String val) {
    Class<?> type = path.getJavaType();

    if (type.equals(Integer.class)) return Integer.valueOf(val);
    if (type.equals(Short.class)) return Short.valueOf(val);
    if (type.equals(Long.class)) return Long.valueOf(val);
    if (type.equals(Byte.class)) return Byte.valueOf(val);
    if (type.equals(Float.class)) return Float.valueOf(val);
    if (type.equals(Double.class)) return Double.valueOf(val);
    if (type.equals(Boolean.class)) return Boolean.valueOf(val);

    return val;
}
    
}
