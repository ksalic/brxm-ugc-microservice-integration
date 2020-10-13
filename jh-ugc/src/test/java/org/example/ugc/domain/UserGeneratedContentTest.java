package org.example.ugc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.example.ugc.web.rest.TestUtil;

public class UserGeneratedContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGeneratedContent.class);
        UserGeneratedContent userGeneratedContent1 = new UserGeneratedContent();
        userGeneratedContent1.setId("id1");
        UserGeneratedContent userGeneratedContent2 = new UserGeneratedContent();
        userGeneratedContent2.setId(userGeneratedContent1.getId());
        assertThat(userGeneratedContent1).isEqualTo(userGeneratedContent2);
        userGeneratedContent2.setId("id2");
        assertThat(userGeneratedContent1).isNotEqualTo(userGeneratedContent2);
        userGeneratedContent1.setId(null);
        assertThat(userGeneratedContent1).isNotEqualTo(userGeneratedContent2);
    }
}
