package br.com.jefferson.salesmanegement.integration;

import br.com.jefferson.salesmanegement.domain.dto.AuthDto;
import br.com.jefferson.salesmanegement.domain.dto.ResponseDto;
import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.domain.repository.UserRepository;
import br.com.jefferson.salesmanegement.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createWhenReturn201CreatedAndToken() throws Exception {
        User user = new User(1L, "name", "jefferson@gmail.com", "123456");
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/user")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        Assertions.assertEquals("http://localhost/v1/user/1", response.getHeaders(HttpHeaders.LOCATION).get(0));
    }

    @Test
    public void createWhenReturn400BadRequestInvalidEmail() throws Exception {
        User user = new User(1L, "name", "jeffersongmailcom", "123456");
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/user")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void createWhenReturn400BadRequestPasswordLessThanSix() throws Exception {
        User user = new User("name", "jefferson@gmail.com", "12345");
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/user")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
