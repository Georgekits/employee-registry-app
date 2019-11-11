package com.unisystems.repository;

import com.unisystems.model.BusinessUnit;
import com.unisystems.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessUnitRepository extends CrudRepository<BusinessUnit, Long> {
    @Override
    @RestResource(exported = false)
    void delete(BusinessUnit entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    public BusinessUnit save(BusinessUnit s);

    @Override
    @RestResource(exported = false)
    <S extends BusinessUnit> Iterable<S> saveAll(Iterable<S> entities);
}
