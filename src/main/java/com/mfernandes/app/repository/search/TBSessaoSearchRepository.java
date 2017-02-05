package com.mfernandes.app.repository.search;

import com.mfernandes.app.domain.TBSessao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TBSessao entity.
 */
public interface TBSessaoSearchRepository extends ElasticsearchRepository<TBSessao, Long> {
}
