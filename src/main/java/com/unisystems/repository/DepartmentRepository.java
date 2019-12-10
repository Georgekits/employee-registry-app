package com.unisystems.repository;

import com.unisystems.model.Company;
import com.unisystems.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void delete(Department entity);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteAll();

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteById(Long aLong);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    Department save(Department s);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    <S extends Department> List<S> saveAll(Iterable<S> entities);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Department> findAll();

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Department> findAllById(Iterable<Long> longs);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    Optional<Department> findById(Long aLong);
}
