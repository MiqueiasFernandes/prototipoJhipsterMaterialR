package com.mfernandes.app.repository;

import com.mfernandes.app.domain.TBBase;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TBBase entity.
 */
@SuppressWarnings("unused")
public interface TBBaseRepository extends JpaRepository<TBBase,Long> {

}
