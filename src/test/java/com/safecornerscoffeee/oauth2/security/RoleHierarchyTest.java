package com.safecornerscoffeee.oauth2.security;

import com.safecornerscoffeee.oauth2.utils.WithMockMemberDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleHierarchyTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockMemberDetails(authorities = "ROLE_MANAGER")
    void manager_role_should_access_user_role_resources() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockMemberDetails(authorities = "ROLE_ADMIN")
    void admin_role_should_access_user_role_resources() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
