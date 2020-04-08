package br.com.jefferson.salesmanegement.integration;

import br.com.jefferson.salesmanegement.SalesmanegementApplication;
import br.com.jefferson.salesmanegement.domain.dto.AuthDto;
import br.com.jefferson.salesmanegement.domain.dto.ResponseDto;
import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.domain.repository.UserRepository;
import br.com.jefferson.salesmanegement.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static final ObjectMapper om = new ObjectMapper();

    private String token;

    private HttpEntity<String> httpEntityDefault;

    @Before
    public void init() {
        User user = User.builder()
                .id(1L)
                .name("jeff")
                .mail("jefferson@bol.com")
                .password(userService.hashPw("123456"))
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByMail("jefferson@bol.com")).thenReturn(Optional.of(user));

        AuthDto authDto = AuthDto.builder().mail("jefferson@bol.com").password("123456").build();
        ResponseEntity<ResponseDto> responseEntity = testRestTemplate.postForEntity("/v1/auth/login", authDto, ResponseDto.class);
        ResponseDto responseDto = responseEntity.getBody();

        this.token = responseDto.getJwt();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.token);

        this.httpEntityDefault = new HttpEntity<>(null, headers);
    }

    @Test
    public void findUserId_OK() throws JSONException {
        String expected = "{id: 1, name:\"jeff\", mail:\"jefferson@bol.com\"}";

        ResponseEntity<String> responseEntity = testRestTemplate
                .exchange("/v1/user/1", HttpMethod.GET, this.httpEntityDefault, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        JSONAssert.assertEquals(expected, responseEntity.getBody(), true);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void findUserId_noContent() throws JSONException {
        String expected = "{id: 1, name:\"jeff\", mail:\"jefferson@bol.com\"}";

        ResponseEntity<String> responseEntity = testRestTemplate
                .exchange("/v1/user/2", HttpMethod.GET, this.httpEntityDefault, String.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        JSONAssert.assertEquals(null, responseEntity.getBody(), true);

        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    public void findUserId_unauthorized() {
        String expected = "{id: 1, name:\"jeff\", mail:\"jefferson@bol.com\"}";

        ResponseEntity<String> responseEntity = testRestTemplate
                .exchange("/v1/user/1", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void findUserEmail_OK() {
        String expected = "{id: 1, name:\"jeff\", mail:\"jefferson@bol.com\"}";

        ResponseEntity<String> responseEntity = testRestTemplate
                .exchange("/v1/user/mail/jefferson@bol.com", HttpMethod.GET, this.httpEntityDefault, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void findUserEmail_noContent() {
        String expected = "{id: 1, name:\"jeff\", mail:\"jefferson@bol.com\"}";

        ResponseEntity<String> responseEntity = testRestTemplate
                .exchange("/v1/user/mail/jefferson@gmail.com", HttpMethod.GET, this.httpEntityDefault, String.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
