package com.mfernandes.app.repository.search;

import com.mfernandes.app.domain.TBProjeto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TBProjeto entity.
 */
public interface TBProjetoSearchRepository extends ElasticsearchRepository<TBProjeto, Long> {
}
