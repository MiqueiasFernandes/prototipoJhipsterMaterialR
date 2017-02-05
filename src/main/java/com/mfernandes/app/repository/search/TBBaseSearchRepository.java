package com.mfernandes.app.repository.search;

import com.mfernandes.app.domain.TBBase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TBBase entity.
 */
public interface TBBaseSearchRepository extends ElasticsearchRepository<TBBase, Long> {
}
