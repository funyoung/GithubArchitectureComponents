package com.boisneyphilippe.githubarchitecturecomponents;

import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.HomePage;
import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.Message;
import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.Page;
import com.boisneyphilippe.githubarchitecturecomponents.data.database.entity.User;
import com.boisneyphilippe.githubarchitecturecomponents.utils.DataResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author yangfeng
 */
@SuppressWarnings("PMD")
public class MomentPojoTest {
    private static class PageResponse extends DataResponse<Page> {}
    private static class HomePageResponse extends DataResponse<HomePage> {}

    private Page page;
    private HomePage homePage;

    private User getUser() {
        return homePage.getUser();
    }

    private Message getMessage() {
        return homePage.getMessage();
    }

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

    @Test
    public void userIdTest() {
        assertNotNull(getUser());
        assertEquals("mbq@sohu.com", getUser().getId());
    }

    @Test
    public void userAvatarTest() {
        assertNotNull(getUser().getAvatar());
        assertEquals("http://img.shouji.sogou.com/wapdl/hole/201910/08/2019100818461982972841.png", getUser().getAvatar());
    }

    @Test
    public void userNameTest() {
        assertEquals("马伯骞", getUser().getNickname());
    }

    @Test
    public void getBackgroundImageURLTest() {
        assertEquals("背景图 url", homePage.getBackgroundImageURL());
    }

    @Test
    public void messageInitTest() {
        assertNotNull(getMessage());
    }

    @Test
    public void messageMemberTest () {
        assertEquals("http://img.shouji.sogou.com/wapdl/hole/201910/09/2019100915215993850240.png", getMessage().getIcon());
        assertEquals("诸葛亮来新消息", getMessage().getText());
        assertEquals(1, getMessage().getMomentID());
    }
}
