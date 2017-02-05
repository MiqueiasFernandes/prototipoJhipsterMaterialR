package com.mfernandes.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mfernandes.app.domain.TBProjeto;

import com.mfernandes.app.repository.TBProjetoRepository;
import com.mfernandes.app.repository.search.TBProjetoSearchRepository;
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
 * REST controller for managing TBProjeto.
 */
@RestController
@RequestMapping("/api")
public class TBProjetoResource {

    private final Logger log = LoggerFactory.getLogger(TBProjetoResource.class);

    private static final String ENTITY_NAME = "tBProjeto";
        
    private final TBProjetoRepository tBProjetoRepository;

    private final TBProjetoSearchRepository tBProjetoSearchRepository;

    public TBProjetoResource(TBProjetoRepository tBProjetoRepository, TBProjetoSearchRepository tBProjetoSearchRepository) {
        this.tBProjetoRepository = tBProjetoRepository;
        this.tBProjetoSearchRepository = tBProjetoSearchRepository;
    }

    /**
     * POST  /t-b-projetos : Create a new tBProjeto.
     *
     * @param tBProjeto the tBProjeto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tBProjeto, or with status 400 (Bad Request) if the tBProjeto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-b-projetos")
    @Timed
    public ResponseEntity<TBProjeto> createTBProjeto(@Valid @RequestBody TBProjeto tBProjeto) throws URISyntaxException {
        log.debug("REST request to save TBProjeto : {}", tBProjeto);
        if (tBProjeto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tBProjeto cannot already have an ID")).body(null);
        }
        TBProjeto result = tBProjetoRepository.save(tBProjeto);
        tBProjetoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/t-b-projetos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-b-projetos : Updates an existing tBProjeto.
     *
     * @param tBProjeto the tBProjeto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tBProjeto,
     * or with status 400 (Bad Request) if the tBProjeto is not valid,
     * or with status 500 (Internal Server Error) if the tBProjeto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-b-projetos")
    @Timed
    public ResponseEntity<TBProjeto> updateTBProjeto(@Valid @RequestBody TBProjeto tBProjeto) throws URISyntaxException {
        log.debug("REST request to update TBProjeto : {}", tBProjeto);
        if (tBProjeto.getId() == null) {
            return createTBProjeto(tBProjeto);
        }
        TBProjeto result = tBProjetoRepository.save(tBProjeto);
        tBProjetoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tBProjeto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-b-projetos : get all the tBProjetos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tBProjetos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/t-b-projetos")
    @Timed
    public ResponseEntity<List<TBProjeto>> getAllTBProjetos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TBProjetos");
        Page<TBProjeto> page = tBProjetoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-b-projetos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /t-b-projetos/:id : get the "id" tBProjeto.
     *
     * @param id the id of the tBProjeto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tBProjeto, or with status 404 (Not Found)
     */
    @GetMapping("/t-b-projetos/{id}")
    @Timed
    public ResponseEntity<TBProjeto> getTBProjeto(@PathVariable Long id) {
        log.debug("REST request to get TBProjeto : {}", id);
        TBProjeto tBProjeto = tBProjetoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tBProjeto));
    }

    /**
     * DELETE  /t-b-projetos/:id : delete the "id" tBProjeto.
     *
     * @param id the id of the tBProjeto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-b-projetos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTBProjeto(@PathVariable Long id) {
        log.debug("REST request to delete TBProjeto : {}", id);
        tBProjetoRepository.delete(id);
        tBProjetoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/t-b-projetos?query=:query : search for the tBProjeto corresponding
     * to the query.
     *
     * @param query the query of the tBProjeto search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/t-b-projetos")
    @Timed
    public ResponseEntity<List<TBProjeto>> searchTBProjetos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of TBProjetos for query {}", query);
        Page<TBProjeto> page = tBProjetoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/t-b-projetos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
