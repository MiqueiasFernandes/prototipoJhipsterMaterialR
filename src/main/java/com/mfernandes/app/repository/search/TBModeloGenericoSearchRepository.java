package com.mfernandes.app.repository.search;

import com.mfernandes.app.domain.TBModeloGenerico;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TBModeloGenerico entity.
 */
public interface TBModeloGenericoSearchRepository extends ElasticsearchRepository<TBModeloGenerico, Long> {
}
