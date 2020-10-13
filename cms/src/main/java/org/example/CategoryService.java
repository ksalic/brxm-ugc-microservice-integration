package org.example;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.bloomreach.cms.openui.model.Result;
import com.bloomreach.cms.openui.rest.ExternalDocumentPickerResource;
import com.bloomreach.cms.openui.rest.PickerItem;

import org.hippoecm.hst.site.HstServices;
import org.onehippo.cms7.crisp.api.broker.ResourceServiceBroker;
import org.onehippo.cms7.crisp.api.exchange.ExchangeHintBuilder;
import org.onehippo.cms7.crisp.api.resource.Resource;
import org.onehippo.cms7.crisp.api.resource.ResourceCollection;
import org.onehippo.cms7.crisp.hst.module.CrispHstServices;
import org.springframework.util.StringUtils;

@Produces({MediaType.APPLICATION_JSON})
@Path("/category")
public class CategoryService implements ExternalDocumentPickerResource {


    @GET
    @Path("/search")
    @Produces({MediaType.APPLICATION_JSON})
    public Result<PickerItem> search(@Context UriInfo uriInfo,
                                     @QueryParam("query") String query,
                                     @QueryParam("page") @DefaultValue("1") int page,
                                     @QueryParam("pageSize") @DefaultValue("20") int pageSize,
                                     @QueryParam("documentLocale") String locale,
                                     @QueryParam("documentId") String documentId) {

        ResourceServiceBroker broker = CrispHstServices.getDefaultResourceServiceBroker(HstServices.getComponentManager());

        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("admin");
        loginVM.setPassword("admin");
        Resource token = broker.resolve("categories", "api/authenticate", ExchangeHintBuilder.create()
            .methodName(HttpMethod.POST)
            .requestHeader("Content-Type", "application/json")
            .requestBody(loginVM)
            .build());

        final String idToken = token.getValue("id_token", String.class);

        Resource content = broker.resolve("categories", "api/categories", ExchangeHintBuilder.create()
            .methodName(HttpMethod.GET)
            .requestHeader("Authorization", "Bearer " + idToken)
            .build());
        ResourceCollection children = content.getChildren();
        List<PickerItem> items = children.getCollection().stream().map(resource -> new DefaultResourceAdapter(resource)).filter(defaultResourceAdapter -> {
            if (!StringUtils.isEmpty(query)) {
                return defaultResourceAdapter.getTitle().toLowerCase().contains(query.toLowerCase());
            }
            return true;
        }).collect(Collectors.toList());
        Result<PickerItem> result = new Result<>(items);

        return result;
    }
}
