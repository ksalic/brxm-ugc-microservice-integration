package org.example.ugc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.example.ugc.model.enumeration.PublicationState;
import org.example.ugc.model.enumeration.UgcState;


/**
 * A UserGeneratedContent.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGeneratedContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String email;

    private String text;

    private String date;

    private String ipAddress;

    private String referenceId;

    private String channelId;

    private Boolean recent;

    private UgcState state;

    private PublicationState publicationState;


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

    public void setName(String name) {
        this.name = name;
    }

    public UserGeneratedContent name(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserGeneratedContent email(String email) {
        this.email = email;
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserGeneratedContent text(String text) {
        this.text = text;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public UserGeneratedContent ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public UserGeneratedContent referenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public UserGeneratedContent channelId(String channelId) {
        this.channelId = channelId;
        return this;
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

    public void setState(UgcState state) {
        this.state = state;
    }

    public UserGeneratedContent state(UgcState state) {
        this.state = state;
        return this;
    }

    public PublicationState getPublicationState() {
        return publicationState;
    }

    public void setPublicationState(PublicationState publicationState) {
        this.publicationState = publicationState;
    }

    public UserGeneratedContent publicationState(PublicationState publicationState) {
        this.publicationState = publicationState;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGeneratedContent)) {
            return false;
        }
        return id != null && id.equals(((UserGeneratedContent)o).id);
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
