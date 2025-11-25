package home.autoai.automation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AutomationApplicationTests {

    @Autowired
    MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
        ResultActions actions = mockMvc.perform(get("/"))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

}
