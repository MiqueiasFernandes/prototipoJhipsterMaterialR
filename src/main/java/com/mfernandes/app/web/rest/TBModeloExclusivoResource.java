package com.mfernandes.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mfernandes.app.domain.TBModeloExclusivo;

import com.mfernandes.app.repository.TBModeloExclusivoRepository;
import com.mfernandes.app.repository.search.TBModeloExclusivoSearchRepository;
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
 * REST controller for managing TBModeloExclusivo.
 */
@RestController
@RequestMapping("/api")
public class TBModeloExclusivoResource {

    private final Logger log = LoggerFactory.getLogger(TBModeloExclusivoResource.class);

    private static final String ENTITY_NAME = "tBModeloExclusivo";
        
    private final TBModeloExclusivoRepository tBModeloExclusivoRepository;

    private final TBModeloExclusivoSearchRepository tBModeloExclusivoSearchRepository;

    public TBModeloExclusivoResource(TBModeloExclusivoRepository tBModeloExclusivoRepository, TBModeloExclusivoSearchRepository tBModeloExclusivoSearchRepository) {
        this.tBModeloExclusivoRepository = tBModeloExclusivoRepository;
        this.tBModeloExclusivoSearchRepository = tBModeloExclusivoSearchRepository;
    }

    /**
     * POST  /t-b-modelo-exclusivos : Create a new tBModeloExclusivo.
     *
     * @param tBModeloExclusivo the tBModeloExclusivo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tBModeloExclusivo, or with status 400 (Bad Request) if the tBModeloExclusivo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-b-modelo-exclusivos")
    @Timed
    public ResponseEntity<TBModeloExclusivo> createTBModeloExclusivo(@Valid @RequestBody TBModeloExclusivo tBModeloExclusivo) throws URISyntaxException {
        log.debug("REST request to save TBModeloExclusivo : {}", tBModeloExclusivo);
        if (tBModeloExclusivo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tBModeloExclusivo cannot already have an ID")).body(null);
        }
        TBModeloExclusivo result = tBModeloExclusivoRepository.save(tBModeloExclusivo);
        tBModeloExclusivoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/t-b-modelo-exclusivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-b-modelo-exclusivos : Updates an existing tBModeloExclusivo.
     *
     * @param tBModeloExclusivo the tBModeloExclusivo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tBModeloExclusivo,
     * or with status 400 (Bad Request) if the tBModeloExclusivo is not valid,
     * or with status 500 (Internal Server Error) if the tBModeloExclusivo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-b-modelo-exclusivos")
    @Timed
    public ResponseEntity<TBModeloExclusivo> updateTBModeloExclusivo(@Valid @RequestBody TBModeloExclusivo tBModeloExclusivo) throws URISyntaxException {
        log.debug("REST request to update TBModeloExclusivo : {}", tBModeloExclusivo);
        if (tBModeloExclusivo.getId() == null) {
            return createTBModeloExclusivo(tBModeloExclusivo);
        }
        TBModeloExclusivo result = tBModeloExclusivoRepository.save(tBModeloExclusivo);
        tBModeloExclusivoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tBModeloExclusivo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-b-modelo-exclusivos : get all the tBModeloExclusivos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tBModeloExclusivos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/t-b-modelo-exclusivos")
    @Timed
    public ResponseEntity<List<TBModeloExclusivo>> getAllTBModeloExclusivos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TBModeloExclusivos");
        Page<TBModeloExclusivo> page = tBModeloExclusivoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-b-modelo-exclusivos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /t-b-modelo-exclusivos/:id : get the "id" tBModeloExclusivo.
     *
     * @param id the id of the tBModeloExclusivo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tBModeloExclusivo, or with status 404 (Not Found)
     */
    @GetMapping("/t-b-modelo-exclusivos/{id}")
    @Timed
    public ResponseEntity<TBModeloExclusivo> getTBModeloExclusivo(@PathVariable Long id) {
        log.debug("REST request to get TBModeloExclusivo : {}", id);
        TBModeloExclusivo tBModeloExclusivo = tBModeloExclusivoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tBModeloExclusivo));
    }

    /**
     * DELETE  /t-b-modelo-exclusivos/:id : delete the "id" tBModeloExclusivo.
     *
     * @param id the id of the tBModeloExclusivo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-b-modelo-exclusivos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTBModeloExclusivo(@PathVariable Long id) {
        log.debug("REST request to delete TBModeloExclusivo : {}", id);
        tBModeloExclusivoRepository.delete(id);
        tBModeloExclusivoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/t-b-modelo-exclusivos?query=:query : search for the tBModeloExclusivo corresponding
     * to the query.
     *
     * @param query the query of the tBModeloExclusivo search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/t-b-modelo-exclusivos")
    @Timed
    public ResponseEntity<List<TBModeloExclusivo>> searchTBModeloExclusivos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of TBModeloExclusivos for query {}", query);
        Page<TBModeloExclusivo> page = tBModeloExclusivoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/t-b-modelo-exclusivos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
