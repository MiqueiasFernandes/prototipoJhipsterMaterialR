package com.mfernandes.app.repository.search;

import com.mfernandes.app.domain.TBUsuario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TBUsuario entity.
 */
public interface TBUsuarioSearchRepository extends ElasticsearchRepository<TBUsuario, Long> {
}
