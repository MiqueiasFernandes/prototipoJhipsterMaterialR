package com.mfernandes.app.repository;

import com.mfernandes.app.domain.TBUsuario;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TBUsuario entity.
 */
@SuppressWarnings("unused")
public interface TBUsuarioRepository extends JpaRepository<TBUsuario,Long> {

}
