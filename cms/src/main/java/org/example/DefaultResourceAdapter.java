package org.example;

import java.util.concurrent.ThreadLocalRandom;

import com.bloomreach.cms.openui.rest.PickerItem;

import org.onehippo.cms7.crisp.api.resource.Resource;

public class DefaultResourceAdapter implements PickerItem<Resource> {

    private String id;
    private String title;
    private String image;
    private String description;
    private Resource data;

    public DefaultResourceAdapter(final Resource resource) {
        this.data = resource;
        this.id = String.valueOf(resource.getValue("id", Integer.class));
        this.title = resource.getValue("displayName", String.class);
//        int randomInt = ThreadLocalRandom.current().nextInt(1, 300 + 1);
        this.image = "https://picsum.photos/seed/" + id + "/373/180";
        this.description = resource.getValue("htmlContentPath", String.class);
    }

    public DefaultResourceAdapter() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Resource getData() {
        return null;
    }
}
