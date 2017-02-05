package com.mfernandes.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mfernandes.app.domain.TBSessao;

import com.mfernandes.app.repository.TBSessaoRepository;
import com.mfernandes.app.repository.search.TBSessaoSearchRepository;
import com.mfernandes.app.web.rest.util.HeaderUtil;
import com.mfernandes.app.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TBSessao.
 */
@RestController
@RequestMapping("/api")
public class TBSessaoResource {

    private final Logger log = LoggerFactory.getLogger(TBSessaoResource.class);

    private static final String ENTITY_NAME = "tBSessao";
        
    private final TBSessaoRepository tBSessaoRepository;

    private final TBSessaoSearchRepository tBSessaoSearchRepository;

    public TBSessaoResource(TBSessaoRepository tBSessaoRepository, TBSessaoSearchRepository tBSessaoSearchRepository) {
        this.tBSessaoRepository = tBSessaoRepository;
        this.tBSessaoSearchRepository = tBSessaoSearchRepository;
    }

    /**
     * POST  /t-b-sessaos : Create a new tBSessao.
     *
     * @param tBSessao the tBSessao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tBSessao, or with status 400 (Bad Request) if the tBSessao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-b-sessaos")
    @Timed
    public ResponseEntity<TBSessao> createTBSessao(@Valid @RequestBody TBSessao tBSessao) throws URISyntaxException {
        log.debug("REST request to save TBSessao : {}", tBSessao);
        if (tBSessao.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tBSessao cannot already have an ID")).body(null);
        }
        TBSessao result = tBSessaoRepository.save(tBSessao);
        tBSessaoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/t-b-sessaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-b-sessaos : Updates an existing tBSessao.
     *
     * @param tBSessao the tBSessao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tBSessao,
     * or with status 400 (Bad Request) if the tBSessao is not valid,
     * or with status 500 (Internal Server Error) if the tBSessao couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-b-sessaos")
    @Timed
    public ResponseEntity<TBSessao> updateTBSessao(@Valid @RequestBody TBSessao tBSessao) throws URISyntaxException {
        log.debug("REST request to update TBSessao : {}", tBSessao);
        if (tBSessao.getId() == null) {
            return createTBSessao(tBSessao);
        }
        TBSessao result = tBSessaoRepository.save(tBSessao);
        tBSessaoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tBSessao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-b-sessaos : get all the tBSessaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tBSessaos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/t-b-sessaos")
    @Timed
    public ResponseEntity<List<TBSessao>> getAllTBSessaos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TBSessaos");
        Page<TBSessao> page = tBSessaoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-b-sessaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /t-b-sessaos/:id : get the "id" tBSessao.
     *
     * @param id the id of the tBSessao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tBSessao, or with status 404 (Not Found)
     */
    @GetMapping("/t-b-sessaos/{id}")
    @Timed
    public ResponseEntity<TBSessao> getTBSessao(@PathVariable Long id) {
        log.debug("REST request to get TBSessao : {}", id);
        TBSessao tBSessao = tBSessaoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tBSessao));
    }

    /**
     * DELETE  /t-b-sessaos/:id : delete the "id" tBSessao.
     *
     * @param id the id of the tBSessao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-b-sessaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTBSessao(@PathVariable Long id) {
        log.debug("REST request to delete TBSessao : {}", id);
        tBSessaoRepository.delete(id);
        tBSessaoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/t-b-sessaos?query=:query : search for the tBSessao corresponding
     * to the query.
     *
     * @param query the query of the tBSessao search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/t-b-sessaos")
    @Timed
    public ResponseEntity<List<TBSessao>> searchTBSessaos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of TBSessaos for query {}", query);
        Page<TBSessao> page = tBSessaoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/t-b-sessaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
