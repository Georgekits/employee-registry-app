package com.unisystems.repository;

import com.unisystems.model.BusinessUnit;
import com.unisystems.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void delete(Company entity);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteAll();

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    void deleteById(Long aLong);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    Company save(Company s);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    <S extends Company> List<S> saveAll(Iterable<S> entities);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Company> findAll();

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    List<Company> findAllById(Iterable<Long> longs);

    @Override
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    Optional<Company> findById(Long aLong);
}