package com.mfernandes.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mfernandes.app.domain.TBModeloGenerico;

import com.mfernandes.app.repository.TBModeloGenericoRepository;
import com.mfernandes.app.repository.search.TBModeloGenericoSearchRepository;
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
 * REST controller for managing TBModeloGenerico.
 */
@RestController
@RequestMapping("/api")
public class TBModeloGenericoResource {

    private final Logger log = LoggerFactory.getLogger(TBModeloGenericoResource.class);

    private static final String ENTITY_NAME = "tBModeloGenerico";
        
    private final TBModeloGenericoRepository tBModeloGenericoRepository;

    private final TBModeloGenericoSearchRepository tBModeloGenericoSearchRepository;

    public TBModeloGenericoResource(TBModeloGenericoRepository tBModeloGenericoRepository, TBModeloGenericoSearchRepository tBModeloGenericoSearchRepository) {
        this.tBModeloGenericoRepository = tBModeloGenericoRepository;
        this.tBModeloGenericoSearchRepository = tBModeloGenericoSearchRepository;
    }

    /**
     * POST  /t-b-modelo-genericos : Create a new tBModeloGenerico.
     *
     * @param tBModeloGenerico the tBModeloGenerico to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tBModeloGenerico, or with status 400 (Bad Request) if the tBModeloGenerico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-b-modelo-genericos")
    @Timed
    public ResponseEntity<TBModeloGenerico> createTBModeloGenerico(@Valid @RequestBody TBModeloGenerico tBModeloGenerico) throws URISyntaxException {
        log.debug("REST request to save TBModeloGenerico : {}", tBModeloGenerico);
        if (tBModeloGenerico.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tBModeloGenerico cannot already have an ID")).body(null);
        }
        TBModeloGenerico result = tBModeloGenericoRepository.save(tBModeloGenerico);
        tBModeloGenericoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/t-b-modelo-genericos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-b-modelo-genericos : Updates an existing tBModeloGenerico.
     *
     * @param tBModeloGenerico the tBModeloGenerico to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tBModeloGenerico,
     * or with status 400 (Bad Request) if the tBModeloGenerico is not valid,
     * or with status 500 (Internal Server Error) if the tBModeloGenerico couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-b-modelo-genericos")
    @Timed
    public ResponseEntity<TBModeloGenerico> updateTBModeloGenerico(@Valid @RequestBody TBModeloGenerico tBModeloGenerico) throws URISyntaxException {
        log.debug("REST request to update TBModeloGenerico : {}", tBModeloGenerico);
        if (tBModeloGenerico.getId() == null) {
            return createTBModeloGenerico(tBModeloGenerico);
        }
        TBModeloGenerico result = tBModeloGenericoRepository.save(tBModeloGenerico);
        tBModeloGenericoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tBModeloGenerico.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-b-modelo-genericos : get all the tBModeloGenericos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tBModeloGenericos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/t-b-modelo-genericos")
    @Timed
    public ResponseEntity<List<TBModeloGenerico>> getAllTBModeloGenericos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TBModeloGenericos");
        Page<TBModeloGenerico> page = tBModeloGenericoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-b-modelo-genericos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /t-b-modelo-genericos/:id : get the "id" tBModeloGenerico.
     *
     * @param id the id of the tBModeloGenerico to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tBModeloGenerico, or with status 404 (Not Found)
     */
    @GetMapping("/t-b-modelo-genericos/{id}")
    @Timed
    public ResponseEntity<TBModeloGenerico> getTBModeloGenerico(@PathVariable Long id) {
        log.debug("REST request to get TBModeloGenerico : {}", id);
        TBModeloGenerico tBModeloGenerico = tBModeloGenericoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tBModeloGenerico));
    }

    /**
     * DELETE  /t-b-modelo-genericos/:id : delete the "id" tBModeloGenerico.
     *
     * @param id the id of the tBModeloGenerico to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-b-modelo-genericos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTBModeloGenerico(@PathVariable Long id) {
        log.debug("REST request to delete TBModeloGenerico : {}", id);
        tBModeloGenericoRepository.delete(id);
        tBModeloGenericoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/t-b-modelo-genericos?query=:query : search for the tBModeloGenerico corresponding
     * to the query.
     *
     * @param query the query of the tBModeloGenerico search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/t-b-modelo-genericos")
    @Timed
    public ResponseEntity<List<TBModeloGenerico>> searchTBModeloGenericos(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of TBModeloGenericos for query {}", query);
        Page<TBModeloGenerico> page = tBModeloGenericoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/t-b-modelo-genericos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
