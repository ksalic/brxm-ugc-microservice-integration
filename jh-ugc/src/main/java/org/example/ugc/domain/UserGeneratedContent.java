package org.example.ugc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.example.ugc.domain.enumeration.UgcState;

import org.example.ugc.domain.enumeration.PublicationState;

/**
 * A UserGeneratedContent.
 */
@Document(collection = "user_generated_content")
public class UserGeneratedContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("email")
    private String email;

    @Field("text")
    private String text;

    @Field("date")
    private ZonedDateTime date;

    @Field("ip_address")
    private String ipAddress;

    @Field("reference_id")
    private String referenceId;

    @Field("channel_id")
    private String channelId;

    @Field("recent")
    private Boolean recent;

    @Field("state")
    private UgcState state;

    @Field("publication_state")
    private PublicationState publicationState;

    @DBRef
    @Field("moderator")
    @JsonIgnoreProperties(value = "userGeneratedContents", allowSetters = true)
    private User moderator;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserGeneratedContent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public UserGeneratedContent email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public UserGeneratedContent text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public UserGeneratedContent date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public UserGeneratedContent ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public UserGeneratedContent referenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getChannelId() {
        return channelId;
    }

    public UserGeneratedContent channelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Boolean isRecent() {
        return recent;
    }

    public UserGeneratedContent recent(Boolean recent) {
        this.recent = recent;
        return this;
    }

    public void setRecent(Boolean recent) {
        this.recent = recent;
    }

    public UgcState getState() {
        return state;
    }

    public UserGeneratedContent state(UgcState state) {
        this.state = state;
        return this;
    }

    public void setState(UgcState state) {
        this.state = state;
    }

    public PublicationState getPublicationState() {
        return publicationState;
    }

    public UserGeneratedContent publicationState(PublicationState publicationState) {
        this.publicationState = publicationState;
        return this;
    }

    public void setPublicationState(PublicationState publicationState) {
        this.publicationState = publicationState;
    }

    public User getModerator() {
        return moderator;
    }

    public UserGeneratedContent moderator(User user) {
        this.moderator = user;
        return this;
    }

    public void setModerator(User user) {
        this.moderator = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGeneratedContent)) {
            return false;
        }
        return id != null && id.equals(((UserGeneratedContent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGeneratedContent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", text='" + getText() + "'" +
            ", date='" + getDate() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", referenceId='" + getReferenceId() + "'" +
            ", channelId='" + getChannelId() + "'" +
            ", recent='" + isRecent() + "'" +
            ", state='" + getState() + "'" +
            ", publicationState='" + getPublicationState() + "'" +
            "}";
    }
}
