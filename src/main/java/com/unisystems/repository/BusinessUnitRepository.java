package com.unisystems.repository;

import com.unisystems.model.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessUnitRepository extends CrudRepository<BusinessUnit, Long> {
    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void delete(BusinessUnit entity);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteAll();

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteById(Long aLong);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    BusinessUnit save(BusinessUnit s);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    <S extends BusinessUnit> List<S> saveAll(Iterable<S> entities);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<BusinessUnit> findAll();

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<BusinessUnit> findAllById(Iterable<Long> longs);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    Optional<BusinessUnit> findById(Long aLong);
}