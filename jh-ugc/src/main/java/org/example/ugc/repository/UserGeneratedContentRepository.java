package org.example.ugc.repository;

import java.util.List;

import org.example.ugc.domain.UserGeneratedContent;
import org.example.ugc.domain.enumeration.PublicationState;
import org.example.ugc.domain.enumeration.UgcState;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserGeneratedContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGeneratedContentRepository extends MongoRepository<UserGeneratedContent, String> {

    List<UserGeneratedContent> findByPublicationStateAndStateAndChannelIdAndReferenceId(PublicationState publicationState, UgcState ugcState, String channelId, String referenceId, Sort sort);

    List<UserGeneratedContent> findByChannelIdAndReferenceId(String channelId, String referenceId, Sort sort);
}
