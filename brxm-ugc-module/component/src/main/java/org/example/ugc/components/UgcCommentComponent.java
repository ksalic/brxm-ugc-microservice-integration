package org.example.ugc.components;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import com.google.common.collect.ImmutableMap;

import org.example.ugc.model.LoginVM;
import org.example.ugc.model.UserGeneratedContent;
import org.example.ugc.model.enumeration.PublicationState;
import org.example.ugc.model.enumeration.UgcState;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.cms7.crisp.api.broker.ResourceServiceBroker;
import org.onehippo.cms7.crisp.api.exchange.ExchangeHintBuilder;
import org.onehippo.cms7.crisp.api.resource.Resource;
import org.onehippo.cms7.crisp.api.resource.ResourceBeanMapper;
import org.onehippo.cms7.crisp.hst.module.CrispHstServices;
import org.onehippo.cms7.essentials.components.CommonComponent;

public class UgcCommentComponent extends CommonComponent {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        super.doBeforeRender(request, response);

        ResourceServiceBroker broker = CrispHstServices.getDefaultResourceServiceBroker(HstServices.getComponentManager());

        final Mount mount = getResolvedMount(request).getMount();

        final String channelId = "PageModelPipeline".equals(mount.getNamedPipeline()) ? mount.getParent().getIdentifier() : mount.getIdentifier();
        final String referenceId = request.getRequestContext().getResolvedSiteMapItem().getPathInfo();

        Map<String, Object> queries = ImmutableMap.of("state", request.getRequestContext().isChannelManagerPreviewRequest() ? "preview" : "live", "channelId", channelId, "referenceId", referenceId);

        Resource comments = broker.resolve("ugc", "api/user-generated-contents/{state}?channelId={channelId}&referenceId={referenceId}", queries, ExchangeHintBuilder.create()
                .methodName(HttpMethod.GET)
                .requestHeader("Content-Type", "application/json")
                .requestHeader("Authorization", "Bearer " + getAuthId())
                .build());

        ResourceBeanMapper resourceBeanMapper = broker.getResourceBeanMapper("ugc");

        final Collection<UserGeneratedContent> userGeneratedContents = resourceBeanMapper.mapCollection(comments.getChildren(), UserGeneratedContent.class);

        request.setModel("comments", Arrays.asList(userGeneratedContents.toArray()));

    }

    private String getAuthId() {
        ResourceServiceBroker broker = CrispHstServices.getDefaultResourceServiceBroker(HstServices.getComponentManager());

        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("admin");
        loginVM.setPassword("admin");
        Resource token = broker.resolve("ugc", "api/authenticate", ExchangeHintBuilder.create()
                .methodName(HttpMethod.POST)
                .requestHeader("Content-Type", "application/json")
                .requestBody(loginVM)
                .build());

        return token.getValue("id_token", String.class);
    }

    @Override
    public void doAction(final HstRequest request, final HstResponse response) throws HstComponentException {
        super.doAction(request, response);

        FormMap map = new FormMap(request, new String[]{"name", "email", "text"});

        ResourceServiceBroker broker = CrispHstServices.getDefaultResourceServiceBroker(HstServices.getComponentManager());

        UserGeneratedContent comment = new UserGeneratedContent();
        comment.setName(map.getField("name").getValue());
        comment.setEmail(map.getField("email").getValue());
        comment.setText(map.getField("text").getValue());
        comment.setDate(FORMAT.format(Calendar.getInstance().getTime()));
        comment.setPublicationState(PublicationState.UNPUBLISHED);
        comment.setRecent(true);
        comment.setState(UgcState.OPEN);

        comment.setChannelId(getResolvedMount(request).getMount().getIdentifier());
        comment.setReferenceId(request.getRequestContext().getResolvedSiteMapItem().getPathInfo());

        Resource post = broker.resolve("ugc", "api/user-generated-contents", ExchangeHintBuilder.create()
                .methodName(HttpMethod.POST)
                .requestHeader("Content-Type", "application/json")
                .requestHeader("Authorization", "Bearer " + getAuthId())
                .requestBody(comment)
                .build());

    }
}
