package com.unisystems.repository;

import com.unisystems.model.Department;
import com.unisystems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void delete(Employee entity);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteAll();

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteById(Long aLong);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    Employee save(Employee s);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    <S extends Employee> List<S> saveAll(Iterable<S> entities);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Employee> findAll();

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Employee> findAllById(Iterable<Long> longs);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    Optional<Employee> findById(Long aLong);
}
