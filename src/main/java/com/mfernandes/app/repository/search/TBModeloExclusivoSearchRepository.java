package com.mfernandes.app.repository.search;

import com.mfernandes.app.domain.TBModeloExclusivo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TBModeloExclusivo entity.
 */
public interface TBModeloExclusivoSearchRepository extends ElasticsearchRepository<TBModeloExclusivo, Long> {
}
