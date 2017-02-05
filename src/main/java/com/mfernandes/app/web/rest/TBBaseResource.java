package com.mfernandes.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mfernandes.app.domain.TBBase;

import com.mfernandes.app.repository.TBBaseRepository;
import com.mfernandes.app.repository.search.TBBaseSearchRepository;
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
 * REST controller for managing TBBase.
 */
@RestController
@RequestMapping("/api")
public class TBBaseResource {

    private final Logger log = LoggerFactory.getLogger(TBBaseResource.class);

    private static final String ENTITY_NAME = "tBBase";
        
    private final TBBaseRepository tBBaseRepository;

    private final TBBaseSearchRepository tBBaseSearchRepository;

    public TBBaseResource(TBBaseRepository tBBaseRepository, TBBaseSearchRepository tBBaseSearchRepository) {
        this.tBBaseRepository = tBBaseRepository;
        this.tBBaseSearchRepository = tBBaseSearchRepository;
    }

    /**
     * POST  /t-b-bases : Create a new tBBase.
     *
     * @param tBBase the tBBase to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tBBase, or with status 400 (Bad Request) if the tBBase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-b-bases")
    @Timed
    public ResponseEntity<TBBase> createTBBase(@Valid @RequestBody TBBase tBBase) throws URISyntaxException {
        log.debug("REST request to save TBBase : {}", tBBase);
        if (tBBase.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tBBase cannot already have an ID")).body(null);
        }
        TBBase result = tBBaseRepository.save(tBBase);
        tBBaseSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/t-b-bases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-b-bases : Updates an existing tBBase.
     *
     * @param tBBase the tBBase to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tBBase,
     * or with status 400 (Bad Request) if the tBBase is not valid,
     * or with status 500 (Internal Server Error) if the tBBase couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-b-bases")
    @Timed
    public ResponseEntity<TBBase> updateTBBase(@Valid @RequestBody TBBase tBBase) throws URISyntaxException {
        log.debug("REST request to update TBBase : {}", tBBase);
        if (tBBase.getId() == null) {
            return createTBBase(tBBase);
        }
        TBBase result = tBBaseRepository.save(tBBase);
        tBBaseSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tBBase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-b-bases : get all the tBBases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tBBases in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/t-b-bases")
    @Timed
    public ResponseEntity<List<TBBase>> getAllTBBases(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TBBases");
        Page<TBBase> page = tBBaseRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-b-bases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /t-b-bases/:id : get the "id" tBBase.
     *
     * @param id the id of the tBBase to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tBBase, or with status 404 (Not Found)
     */
    @GetMapping("/t-b-bases/{id}")
    @Timed
    public ResponseEntity<TBBase> getTBBase(@PathVariable Long id) {
        log.debug("REST request to get TBBase : {}", id);
        TBBase tBBase = tBBaseRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tBBase));
    }

    /**
     * DELETE  /t-b-bases/:id : delete the "id" tBBase.
     *
     * @param id the id of the tBBase to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-b-bases/{id}")
    @Timed
    public ResponseEntity<Void> deleteTBBase(@PathVariable Long id) {
        log.debug("REST request to delete TBBase : {}", id);
        tBBaseRepository.delete(id);
        tBBaseSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/t-b-bases?query=:query : search for the tBBase corresponding
     * to the query.
     *
     * @param query the query of the tBBase search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/t-b-bases")
    @Timed
    public ResponseEntity<List<TBBase>> searchTBBases(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of TBBases for query {}", query);
        Page<TBBase> page = tBBaseSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/t-b-bases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
