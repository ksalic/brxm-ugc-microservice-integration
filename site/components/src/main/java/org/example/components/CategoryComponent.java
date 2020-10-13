package org.example.components;

import javax.ws.rs.HttpMethod;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.StringUtils;
//import org.example.beans.Category;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.cms7.crisp.api.broker.ResourceServiceBroker;
import org.onehippo.cms7.crisp.api.exchange.ExchangeHintBuilder;
import org.onehippo.cms7.crisp.api.resource.Resource;
import org.onehippo.cms7.crisp.hst.module.CrispHstServices;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;

public class CategoryComponent extends EssentialsContentComponent {

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        super.doBeforeRender(request, response);

//        final Category category = request.getRequestContext().getContentBean(Category.class);
//        if (StringUtils.isNotEmpty(category.getCategoryId())) {
//            ResourceServiceBroker broker = CrispHstServices.getDefaultResourceServiceBroker(HstServices.getComponentManager());
//
//            LoginVM loginVM = new LoginVM();
//            loginVM.setUsername("admin");
//            loginVM.setPassword("admin");
//            Resource token = broker.resolve("categories", "api/authenticate", ExchangeHintBuilder.create()
//                .methodName(HttpMethod.POST)
//                .requestHeader("Content-Type", "application/json")
//                .requestBody(loginVM)
//                .build());
//
//            final String idToken = token.getValue("id_token", String.class);
//
//            Resource content = broker.resolve("categories", "api/categories/{id}", ImmutableMap.of("id", category.getCategoryId()), ExchangeHintBuilder.create()
//                .methodName(HttpMethod.GET)
//                .requestHeader("Content-Type", "application/json")
//                .requestHeader("Authorization", "Bearer " + idToken)
//                .build());
//
//            request.setModel("categoryItem", content.getNodeData());
//            request.setAttribute("categoryItem", content.getNodeData());
//
//        }


    }
}
