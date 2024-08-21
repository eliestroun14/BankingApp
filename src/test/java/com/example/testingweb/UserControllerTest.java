package com.example.testingweb;

import com.exercise.exercisebankingapp.ExerciseBankingAppApplication;
import com.exercise.exercisebankingapp.controller.UserController;
import com.exercise.exercisebankingapp.dataTransferObject.AuthenticationRequest;
import com.exercise.exercisebankingapp.dataTransferObject.UserRegisterDTO;
import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.entity.MyUserDetails;
import com.exercise.exercisebankingapp.security.util.JwtUtil;
import com.exercise.exercisebankingapp.service.MyUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ExerciseBankingAppApplication.class
)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    private ObjectMapper objectMapper;

    @InjectMocks
    private UserController userController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testLogin() throws Exception {
        // Create a MyUser instance
        MyUser testUser = new MyUser("testUser", "user@gmail.com", LocalDate.of(2000, 1, 1), "randomAddress", "1234567890", MyUser.Role.USER, "{bcrypt}userPass", MyUser.Status.ACTIVE);

        // Wrap MyUser in MyUserDetails
        UserDetails testUserDetails = new MyUserDetails(testUser);

        // Mock authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(testUserDetails, null, testUserDetails.getAuthorities());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(myUserDetailsService.loadUserByUsername("testUser")).thenReturn(testUserDetails);
        when(jwtUtil.generateToken(testUserDetails)).thenReturn("testJwtToken");

        // Perform test
        AuthenticationRequest request = new AuthenticationRequest("testUser", "userPass");
        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("testJwtToken"));
    }

    @Test
    public void registerShouldReturnJwtAndOkStatus() throws Exception {
        // Create a MyUser instance for the registered user
        MyUser newUser = new MyUser(
                "testUser",
                "user@gmail.com",
                LocalDate.of(2004, 5, 31),
                "randomAddress",
                "06 75 15 82 22",
                MyUser.Role.USER,
                "{bcrypt}userPass",
                MyUser.Status.ONBOARDING
        );

        // Wrap MyUser in MyUserDetails
        UserDetails newUserDetails = new MyUserDetails(newUser);

        // Mock the behavior of authenticationManager
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUserDetails, null, newUserDetails.getAuthorities());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Mock UserDetailsService to return the UserDetails object
        when(myUserDetailsService.loadUserByUsername(newUser.getName())).thenReturn(newUserDetails);

        // Mock JWT token generation
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("mocked-jwt-token");

        // Create the registration request
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(
                newUser.getName(),
                newUser.getEmail(),
                newUser.getDateOfBirth(),
                newUser.getAddress(),
                newUser.getPhoneNumber(),
                newUser.getRole(),
                "userPass",
                newUser.getStatus()
        );

        // Perform the request and capture the result
        ResultActions resultActions = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterDTO)));

        // Print the response body for debugging
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Test response body: " + "/" + responseBody + "/");

        // Verify the response
        resultActions
                .andExpect(jsonPath("$.body.jwt").value("mocked-jwt-token"))
                .andExpect(status().is(200));
    }

}

