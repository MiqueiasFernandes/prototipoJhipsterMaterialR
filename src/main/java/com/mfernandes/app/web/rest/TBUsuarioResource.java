package com.mfernandes.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mfernandes.app.domain.TBUsuario;

import com.mfernandes.app.repository.TBUsuarioRepository;
import com.mfernandes.app.repository.search.TBUsuarioSearchRepository;
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
 * REST controller for managing TBUsuario.
 */
@RestController
@RequestMapping("/api")
public class TBUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(TBUsuarioResource.class);

    private static final String ENTITY_NAME = "tBUsuario";
        
    private final TBUsuarioRepository tBUsuarioRepository;

    private final TBUsuarioSearchRepository tBUsuarioSearchRepository;

    public TBUsuarioResource(TBUsuarioRepository tBUsuarioRepository, TBUsuarioSearchRepository tBUsuarioSearchRepository) {
        this.tBUsuarioRepository = tBUsuarioRepository;
        this.tBUsuarioSearchRepository = tBUsuarioSearchRepository;
    }

    /**
     * POST  /t-b-usuarios : Create a new tBUsuario.
     *
     * @param tBUsuario the tBUsuario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tBUsuario, or with status 400 (Bad Request) if the tBUsuario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/t-b-usuarios")
    @Timed
    public ResponseEntity<TBUsuario> createTBUsuario(@Valid @RequestBody TBUsuario tBUsuario) throws URISyntaxException {
        log.debug("REST request to save TBUsuario : {}", tBUsuario);
        if (tBUsuario.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tBUsuario cannot already have an ID")).body(null);
        }
        TBUsuario result = tBUsuarioRepository.save(tBUsuario);
        tBUsuarioSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/t-b-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /t-b-usuarios : Updates an existing tBUsuario.
     *
     * @param tBUsuario the tBUsuario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tBUsuario,
     * or with status 400 (Bad Request) if the tBUsuario is not valid,
     * or with status 500 (Internal Server Error) if the tBUsuario couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/t-b-usuarios")
    @Timed
    public ResponseEntity<TBUsuario> updateTBUsuario(@Valid @RequestBody TBUsuario tBUsuario) throws URISyntaxException {
        log.debug("REST request to update TBUsuario : {}", tBUsuario);
        if (tBUsuario.getId() == null) {
            return createTBUsuario(tBUsuario);
        }
        TBUsuario result = tBUsuarioRepository.save(tBUsuario);
        tBUsuarioSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tBUsuario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /t-b-usuarios : get all the tBUsuarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tBUsuarios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/t-b-usuarios")
    @Timed
    public ResponseEntity<List<TBUsuario>> getAllTBUsuarios(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TBUsuarios");
        Page<TBUsuario> page = tBUsuarioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/t-b-usuarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /t-b-usuarios/:id : get the "id" tBUsuario.
     *
     * @param id the id of the tBUsuario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tBUsuario, or with status 404 (Not Found)
     */
    @GetMapping("/t-b-usuarios/{id}")
    @Timed
    public ResponseEntity<TBUsuario> getTBUsuario(@PathVariable Long id) {
        log.debug("REST request to get TBUsuario : {}", id);
        TBUsuario tBUsuario = tBUsuarioRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tBUsuario));
    }

    /**
     * DELETE  /t-b-usuarios/:id : delete the "id" tBUsuario.
     *
     * @param id the id of the tBUsuario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/t-b-usuarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteTBUsuario(@PathVariable Long id) {
        log.debug("REST request to delete TBUsuario : {}", id);
        tBUsuarioRepository.delete(id);
        tBUsuarioSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/t-b-usuarios?query=:query : search for the tBUsuario corresponding
     * to the query.
     *
     * @param query the query of the tBUsuario search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/t-b-usuarios")
    @Timed
    public ResponseEntity<List<TBUsuario>> searchTBUsuarios(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of TBUsuarios for query {}", query);
        Page<TBUsuario> page = tBUsuarioSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/t-b-usuarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
