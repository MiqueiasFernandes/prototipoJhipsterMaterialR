package com.mfernandes.app.repository;

import com.mfernandes.app.domain.TBModeloGenerico;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TBModeloGenerico entity.
 */
@SuppressWarnings("unused")
public interface TBModeloGenericoRepository extends JpaRepository<TBModeloGenerico,Long> {

}
