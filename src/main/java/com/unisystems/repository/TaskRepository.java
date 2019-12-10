package com.unisystems.repository;

import com.unisystems.model.Employee;
import com.unisystems.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void delete(Task entity);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteAll();

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteById(Long aLong);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    Task save(Task s);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    <S extends Task> List<S> saveAll(Iterable<S> entities);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Task> findAll();

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Task> findAllById(Iterable<Long> longs);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    Optional<Task> findById(Long aLong);
}