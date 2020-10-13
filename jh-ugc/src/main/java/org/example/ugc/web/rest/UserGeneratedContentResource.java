package org.example.ugc.web.rest;

import org.example.ugc.domain.UserGeneratedContent;
import org.example.ugc.domain.enumeration.PublicationState;
import org.example.ugc.domain.enumeration.UgcState;
import org.example.ugc.repository.UserGeneratedContentRepository;
import org.example.ugc.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.example.ugc.domain.UserGeneratedContent}.
 */
@RestController
@RequestMapping("/api")
public class UserGeneratedContentResource {

    private final Logger log = LoggerFactory.getLogger(UserGeneratedContentResource.class);

    private static final String ENTITY_NAME = "userGeneratedContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGeneratedContentRepository userGeneratedContentRepository;

    public UserGeneratedContentResource(UserGeneratedContentRepository userGeneratedContentRepository) {
        this.userGeneratedContentRepository = userGeneratedContentRepository;
    }

    /**
     * {@code POST  /user-generated-contents} : Create a new userGeneratedContent.
     *
     * @param userGeneratedContent the userGeneratedContent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGeneratedContent, or with status {@code 400 (Bad Request)} if the userGeneratedContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-generated-contents")
    public ResponseEntity<UserGeneratedContent> createUserGeneratedContent(@RequestBody UserGeneratedContent userGeneratedContent) throws URISyntaxException {
        log.debug("REST request to save UserGeneratedContent : {}", userGeneratedContent);
        if (userGeneratedContent.getId() != null) {
            throw new BadRequestAlertException("A new userGeneratedContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGeneratedContent result = userGeneratedContentRepository.save(userGeneratedContent);
        return ResponseEntity.created(new URI("/api/user-generated-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-generated-contents} : Updates an existing userGeneratedContent.
     *
     * @param userGeneratedContent the userGeneratedContent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGeneratedContent,
     * or with status {@code 400 (Bad Request)} if the userGeneratedContent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGeneratedContent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-generated-contents")
    public ResponseEntity<UserGeneratedContent> updateUserGeneratedContent(@RequestBody UserGeneratedContent userGeneratedContent) throws URISyntaxException {
        log.debug("REST request to update UserGeneratedContent : {}", userGeneratedContent);
        if (userGeneratedContent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserGeneratedContent result = userGeneratedContentRepository.save(userGeneratedContent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userGeneratedContent.getId()))
            .body(result);
    }

    /**
     * {@code GET  /user-generated-contents} : get all the userGeneratedContents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGeneratedContents in body.
     */
    @GetMapping("/user-generated-contents")
    public List<UserGeneratedContent> getAllUserGeneratedContents() {
        log.debug("REST request to get all UserGeneratedContents");
        return userGeneratedContentRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @GetMapping("/user-generated-contents/live")
    public List<UserGeneratedContent> getAllLiveUserGeneratedContents(@RequestParam String channelId, @RequestParam String referenceId) {
        log.debug("REST request to get all UserGeneratedContents");
        return userGeneratedContentRepository.findByPublicationStateAndStateAndChannelIdAndReferenceId(PublicationState.PUBLISHED, UgcState.CLOSE, channelId, referenceId, Sort.by(Sort.Direction.DESC, "date"));
    }


    @GetMapping("/user-generated-contents/preview")
    public List<UserGeneratedContent> getAllPreviewUserGeneratedContents(@RequestParam String channelId, @RequestParam String referenceId) {
        log.debug("REST request to get all UserGeneratedContents");
        return userGeneratedContentRepository.findByChannelIdAndReferenceId(channelId, referenceId, Sort.by(Sort.Direction.DESC, "date"));
    }

    /**
     * {@code GET  /user-generated-contents/:id} : get the "id" userGeneratedContent.
     *
     * @param id the id of the userGeneratedContent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGeneratedContent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-generated-contents/{id}")
    public ResponseEntity<UserGeneratedContent> getUserGeneratedContent(@PathVariable String id) {
        log.debug("REST request to get UserGeneratedContent : {}", id);
        Optional<UserGeneratedContent> userGeneratedContent = userGeneratedContentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userGeneratedContent);
    }

    /**
     * {@code DELETE  /user-generated-contents/:id} : delete the "id" userGeneratedContent.
     *
     * @param id the id of the userGeneratedContent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-generated-contents/{id}")
    public ResponseEntity<Void> deleteUserGeneratedContent(@PathVariable String id) {
        log.debug("REST request to delete UserGeneratedContent : {}", id);
        userGeneratedContentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
