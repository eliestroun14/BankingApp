package com.example.testingweb;

import com.exercise.exercisebankingapp.ExerciseBankingAppApplication;
import com.exercise.exercisebankingapp.controller.UserController;
import com.exercise.exercisebankingapp.dataTransferObject.UserRegisterDTO;
import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.repository.UserRepository;
import com.exercise.exercisebankingapp.service.UserService;
import org.assertj.core.util.diff.myers.MyersDiff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = ExerciseBankingAppApplication.class
)
class HttpRequestTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String linkHeader;

    @BeforeEach
    void setup() {
        linkHeader = "http://localhost:" + port;
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject(linkHeader + "/api/user/mytest",
                String.class)).contains("Hello World!");
    }

    @Test
    void logoutShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject(linkHeader + "/api/user/logout",
                String.class)).contains("Logout successful");
    }
}

    /*

    @Test
    void registerEndPoint() throws Exception {

        MyUser user = new MyUser(
                "testUser",
                "user@gmail.com",
                LocalDate.of(1990, 5, 31),
                "randomAddress",
                "06 75 15 82 22",
                MyUser.Role.USER,
                "userPass",
                MyUser.Status.ONBOARDING
        );
        userRepository.save(user);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/user/register",
                String.class)).contains();
    }


} */
