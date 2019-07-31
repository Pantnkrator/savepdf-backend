package com.umsa.savepdf.web.rest;

import com.umsa.savepdf.service.FormService;
import com.umsa.savepdf.web.rest.errors.BadRequestAlertException;
import com.umsa.savepdf.service.dto.FormDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.umsa.savepdf.domain.Form}.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    private static final String ENTITY_NAME = "form";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormService formService;

    public FormResource(FormService formService) {
        this.formService = formService;
    }

    /**
     * {@code POST  /forms} : Create a new form.
     *
     * @param formDTO the formDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formDTO, or with status {@code 400 (Bad Request)} if the form has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forms")
    public ResponseEntity<FormDTO> createForm(@RequestBody FormDTO formDTO) throws URISyntaxException {
        log.debug("REST request to save Form : {}", formDTO);
        if (formDTO.getId() != null) {
            throw new BadRequestAlertException("A new form cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormDTO result = formService.save(formDTO);
        return ResponseEntity.created(new URI("/api/forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forms} : Updates an existing form.
     *
     * @param formDTO the formDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formDTO,
     * or with status {@code 400 (Bad Request)} if the formDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forms")
    public ResponseEntity<FormDTO> updateForm(@RequestBody FormDTO formDTO) throws URISyntaxException {
        log.debug("REST request to update Form : {}", formDTO);
        if (formDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormDTO result = formService.save(formDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forms} : get all the forms.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of forms in body.
     */
    @GetMapping("/forms")
    public ResponseEntity<List<FormDTO>> getAllForms(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Forms");
        Page<FormDTO> page = formService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /forms/:id} : get the "id" form.
     *
     * @param id the id of the formDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forms/{id}")
    public ResponseEntity<FormDTO> getForm(@PathVariable Long id) {
        log.debug("REST request to get Form : {}", id);
        Optional<FormDTO> formDTO = formService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formDTO);
    }

    /**
     * {@code DELETE  /forms/:id} : delete the "id" form.
     *
     * @param id the id of the formDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forms/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        log.debug("REST request to delete Form : {}", id);
        formService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
