package com.unisystems.repository;

import com.unisystems.model.Company;
import com.unisystems.model.Unit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Long> {
    @Override
    @RestResource(exported = false)
    void delete(Unit entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    public Unit save(Unit s);

    @Override
    @RestResource(exported = false)
    <S extends Unit> Iterable<S> saveAll(Iterable<S> entities);
}
