package com.boisneyphilippe.githubarchitecturecomponents;

import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.HomePage;
import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.Page;
import com.boisneyphilippe.githubarchitecturecomponents.utils.DataResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author yangfeng
 */
public class MomentPojoTest {
    private static class PageResponse extends DataResponse<Page> {}
    private static class HomePageResponse extends DataResponse<HomePage> {}

    private Page page;
    private HomePage homePage;

    @Before
    public void init() {
        page = DataResponse.from(SampleMomentConstant.SAMPLE_JSON, PageResponse.class);
        homePage = DataResponse.from(SampleMomentConstant.SAMPLE_JSON, HomePageResponse.class);
    }

    @Test
    public void initPageCorrect() {
        assertNotNull(page);
        assertNotNull(homePage);
    }


}
