package com.unisystems.repository;

import com.unisystems.model.Company;
import com.unisystems.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
    @Override
    @RestResource(exported = false)
    void delete(Department entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    public Department save(Department s);

    @Override
    @RestResource(exported = false)
    <S extends Department> Iterable<S> saveAll(Iterable<S> entities);
}
