package com.unisystems.repository;

import com.unisystems.model.Department;
import com.unisystems.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Long> {
    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void delete(Unit entity);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteAll();

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteById(Long aLong);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Unit save(Unit s);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    <S extends Unit> List<S> saveAll(Iterable<S> entities);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Unit> findAll();

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Unit> findAllById(Iterable<Long> longs);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    Optional<Unit> findById(Long aLong);
}