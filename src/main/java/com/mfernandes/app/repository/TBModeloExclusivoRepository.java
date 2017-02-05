package com.mfernandes.app.repository;

import com.mfernandes.app.domain.TBModeloExclusivo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TBModeloExclusivo entity.
 */
@SuppressWarnings("unused")
public interface TBModeloExclusivoRepository extends JpaRepository<TBModeloExclusivo,Long> {

}
