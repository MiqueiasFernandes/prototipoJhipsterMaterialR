package com.mfernandes.app.repository;

import com.mfernandes.app.domain.TBProjeto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TBProjeto entity.
 */
@SuppressWarnings("unused")
public interface TBProjetoRepository extends JpaRepository<TBProjeto,Long> {

}
