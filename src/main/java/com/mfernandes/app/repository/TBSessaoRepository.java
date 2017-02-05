package com.mfernandes.app.repository;

import com.mfernandes.app.domain.TBSessao;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TBSessao entity.
 */
@SuppressWarnings("unused")
public interface TBSessaoRepository extends JpaRepository<TBSessao,Long> {

}
