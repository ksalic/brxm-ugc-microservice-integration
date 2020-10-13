package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.repository.ext.DerivedDataFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryDerivedFunction extends DerivedDataFunction {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Logger log = LoggerFactory.getLogger(CategoryDerivedFunction.class);

    @Override
    public Map<String, Value[]> compute(final Map<String, Value[]> map) {
        try {
            if (map.containsKey("openuid") && map.get("openuid") != null) {
                String openuidString = map.get("openuid")[0].getString();
                if (StringUtils.isNotEmpty(openuidString)) {
                    List<DefaultResourceAdapter> items = MAPPER.readValue(openuidString, MAPPER.getTypeFactory().constructCollectionType(List.class, DefaultResourceAdapter.class));
                    Value[] values = items.stream()
                            .map(pickerItemAdapter -> getValueFactory().createValue(pickerItemAdapter.getId()))
                            .toArray(Value[]::new);
                    map.put("path", values);
                } else {
                    map.put("path", new Value[]{});
                }
            }
        } catch (RepositoryException | IOException e) {
            log.error("error while trying to derive path for category:", e);
        }
        return map;
    }
}
