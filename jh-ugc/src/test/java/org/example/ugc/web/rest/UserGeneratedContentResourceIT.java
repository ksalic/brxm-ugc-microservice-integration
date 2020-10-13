package org.example.ugc.web.rest;

import org.example.ugc.UgcApp;
import org.example.ugc.domain.UserGeneratedContent;
import org.example.ugc.repository.UserGeneratedContentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.example.ugc.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.ugc.domain.enumeration.UgcState;
import org.example.ugc.domain.enumeration.PublicationState;
/**
 * Integration tests for the {@link UserGeneratedContentResource} REST controller.
 */
@SpringBootTest(classes = UgcApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserGeneratedContentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CHANNEL_ID = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RECENT = false;
    private static final Boolean UPDATED_RECENT = true;

    private static final UgcState DEFAULT_STATE = UgcState.OPEN;
    private static final UgcState UPDATED_STATE = UgcState.CLOSE;

    private static final PublicationState DEFAULT_PUBLICATION_STATE = PublicationState.PUBLISHED;
    private static final PublicationState UPDATED_PUBLICATION_STATE = PublicationState.UNPUBLISHED;

    @Autowired
    private UserGeneratedContentRepository userGeneratedContentRepository;

    @Autowired
    private MockMvc restUserGeneratedContentMockMvc;

    private UserGeneratedContent userGeneratedContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGeneratedContent createEntity() {
        UserGeneratedContent userGeneratedContent = new UserGeneratedContent()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .text(DEFAULT_TEXT)
            .date(DEFAULT_DATE)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .referenceId(DEFAULT_REFERENCE_ID)
            .channelId(DEFAULT_CHANNEL_ID)
            .recent(DEFAULT_RECENT)
            .state(DEFAULT_STATE)
            .publicationState(DEFAULT_PUBLICATION_STATE);
        return userGeneratedContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGeneratedContent createUpdatedEntity() {
        UserGeneratedContent userGeneratedContent = new UserGeneratedContent()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .text(UPDATED_TEXT)
            .date(UPDATED_DATE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .referenceId(UPDATED_REFERENCE_ID)
            .channelId(UPDATED_CHANNEL_ID)
            .recent(UPDATED_RECENT)
            .state(UPDATED_STATE)
            .publicationState(UPDATED_PUBLICATION_STATE);
        return userGeneratedContent;
    }

    @BeforeEach
    public void initTest() {
        userGeneratedContentRepository.deleteAll();
        userGeneratedContent = createEntity();
    }

    @Test
    public void createUserGeneratedContent() throws Exception {
        int databaseSizeBeforeCreate = userGeneratedContentRepository.findAll().size();
        // Create the UserGeneratedContent
        restUserGeneratedContentMockMvc.perform(post("/api/user-generated-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGeneratedContent)))
            .andExpect(status().isCreated());

        // Validate the UserGeneratedContent in the database
        List<UserGeneratedContent> userGeneratedContentList = userGeneratedContentRepository.findAll();
        assertThat(userGeneratedContentList).hasSize(databaseSizeBeforeCreate + 1);
        UserGeneratedContent testUserGeneratedContent = userGeneratedContentList.get(userGeneratedContentList.size() - 1);
        assertThat(testUserGeneratedContent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserGeneratedContent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserGeneratedContent.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testUserGeneratedContent.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testUserGeneratedContent.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testUserGeneratedContent.getReferenceId()).isEqualTo(DEFAULT_REFERENCE_ID);
        assertThat(testUserGeneratedContent.getChannelId()).isEqualTo(DEFAULT_CHANNEL_ID);
        assertThat(testUserGeneratedContent.isRecent()).isEqualTo(DEFAULT_RECENT);
        assertThat(testUserGeneratedContent.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testUserGeneratedContent.getPublicationState()).isEqualTo(DEFAULT_PUBLICATION_STATE);
    }

    @Test
    public void createUserGeneratedContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGeneratedContentRepository.findAll().size();

        // Create the UserGeneratedContent with an existing ID
        userGeneratedContent.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGeneratedContentMockMvc.perform(post("/api/user-generated-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGeneratedContent)))
            .andExpect(status().isBadRequest());

        // Validate the UserGeneratedContent in the database
        List<UserGeneratedContent> userGeneratedContentList = userGeneratedContentRepository.findAll();
        assertThat(userGeneratedContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllUserGeneratedContents() throws Exception {
        // Initialize the database
        userGeneratedContentRepository.save(userGeneratedContent);

        // Get all the userGeneratedContentList
        restUserGeneratedContentMockMvc.perform(get("/api/user-generated-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGeneratedContent.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID)))
            .andExpect(jsonPath("$.[*].channelId").value(hasItem(DEFAULT_CHANNEL_ID)))
            .andExpect(jsonPath("$.[*].recent").value(hasItem(DEFAULT_RECENT.booleanValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].publicationState").value(hasItem(DEFAULT_PUBLICATION_STATE.toString())));
    }
    
    @Test
    public void getUserGeneratedContent() throws Exception {
        // Initialize the database
        userGeneratedContentRepository.save(userGeneratedContent);

        // Get the userGeneratedContent
        restUserGeneratedContentMockMvc.perform(get("/api/user-generated-contents/{id}", userGeneratedContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGeneratedContent.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.referenceId").value(DEFAULT_REFERENCE_ID))
            .andExpect(jsonPath("$.channelId").value(DEFAULT_CHANNEL_ID))
            .andExpect(jsonPath("$.recent").value(DEFAULT_RECENT.booleanValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.publicationState").value(DEFAULT_PUBLICATION_STATE.toString()));
    }
    @Test
    public void getNonExistingUserGeneratedContent() throws Exception {
        // Get the userGeneratedContent
        restUserGeneratedContentMockMvc.perform(get("/api/user-generated-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserGeneratedContent() throws Exception {
        // Initialize the database
        userGeneratedContentRepository.save(userGeneratedContent);

        int databaseSizeBeforeUpdate = userGeneratedContentRepository.findAll().size();

        // Update the userGeneratedContent
        UserGeneratedContent updatedUserGeneratedContent = userGeneratedContentRepository.findById(userGeneratedContent.getId()).get();
        updatedUserGeneratedContent
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .text(UPDATED_TEXT)
            .date(UPDATED_DATE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .referenceId(UPDATED_REFERENCE_ID)
            .channelId(UPDATED_CHANNEL_ID)
            .recent(UPDATED_RECENT)
            .state(UPDATED_STATE)
            .publicationState(UPDATED_PUBLICATION_STATE);

        restUserGeneratedContentMockMvc.perform(put("/api/user-generated-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserGeneratedContent)))
            .andExpect(status().isOk());

        // Validate the UserGeneratedContent in the database
        List<UserGeneratedContent> userGeneratedContentList = userGeneratedContentRepository.findAll();
        assertThat(userGeneratedContentList).hasSize(databaseSizeBeforeUpdate);
        UserGeneratedContent testUserGeneratedContent = userGeneratedContentList.get(userGeneratedContentList.size() - 1);
        assertThat(testUserGeneratedContent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserGeneratedContent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserGeneratedContent.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testUserGeneratedContent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testUserGeneratedContent.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testUserGeneratedContent.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
        assertThat(testUserGeneratedContent.getChannelId()).isEqualTo(UPDATED_CHANNEL_ID);
        assertThat(testUserGeneratedContent.isRecent()).isEqualTo(UPDATED_RECENT);
        assertThat(testUserGeneratedContent.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testUserGeneratedContent.getPublicationState()).isEqualTo(UPDATED_PUBLICATION_STATE);
    }

    @Test
    public void updateNonExistingUserGeneratedContent() throws Exception {
        int databaseSizeBeforeUpdate = userGeneratedContentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGeneratedContentMockMvc.perform(put("/api/user-generated-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGeneratedContent)))
            .andExpect(status().isBadRequest());

        // Validate the UserGeneratedContent in the database
        List<UserGeneratedContent> userGeneratedContentList = userGeneratedContentRepository.findAll();
        assertThat(userGeneratedContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUserGeneratedContent() throws Exception {
        // Initialize the database
        userGeneratedContentRepository.save(userGeneratedContent);

        int databaseSizeBeforeDelete = userGeneratedContentRepository.findAll().size();

        // Delete the userGeneratedContent
        restUserGeneratedContentMockMvc.perform(delete("/api/user-generated-contents/{id}", userGeneratedContent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGeneratedContent> userGeneratedContentList = userGeneratedContentRepository.findAll();
        assertThat(userGeneratedContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
