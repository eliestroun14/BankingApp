package com.example.testingweb;

import com.exercise.exercisebankingapp.ExerciseBankingAppApplication;
import com.exercise.exercisebankingapp.controller.UserController;
import com.exercise.exercisebankingapp.dataTransferObject.AuthenticationRequest;
import com.exercise.exercisebankingapp.dataTransferObject.UserRegisterDTO;
import com.exercise.exercisebankingapp.entity.Account;
import com.exercise.exercisebankingapp.entity.MyUser;
import com.exercise.exercisebankingapp.entity.MyUserDetails;
import com.exercise.exercisebankingapp.repository.AccountRepository;
import com.exercise.exercisebankingapp.repository.UserRepository;
import com.exercise.exercisebankingapp.security.util.JwtUtil;
import com.exercise.exercisebankingapp.service.AccountService;
import com.exercise.exercisebankingapp.service.MyUserDetailsService;
import com.exercise.exercisebankingapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(SpringExtension.class)
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

    //@MockBean
    //private AccountService accountService;

    @MockBean
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private ObjectMapper objectMapper;
    @Autowired
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
        ResultActions resultActions = mockMvc.perform(post("/api/user/login")
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
        String token = "mocked-jwt-token";
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn(token);

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


    @Test
    public void testGetMyAccounts() throws Exception {
        // Create a MyUser instance for the authenticated user
        MyUser user = new MyUser(
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
        UserDetails userDetails = new MyUserDetails(user);

        // Mock the JWT token and its validation
        String token = "mocked-jwt-token";
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn(token);
        when(jwtUtil.extractUsername(token)).thenReturn(user.getName());
        when(jwtUtil.validateToken(token, userDetails)).thenReturn(true);

        // Mock UserDetailsService to return the UserDetails object
        when(myUserDetailsService.loadUserByUsername(user.getName())).thenReturn(userDetails);

        // Mock the UserService to return the MyUser object
        when(userService.getUserByName(user.getName())).thenReturn(user);

        // Prepare the account details
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("AAFF12380 107");
        account.setAccountType(Account.AccountType.PERSONAL);
        account.setAccountStatus(MyUser.Status.ONBOARDING);
        account.setBalance(100.0);

        user.setAccounts(new ArrayList<>(Collections.singleton(account)));

        // Mock the service call that retrieves the accounts
        when(accountService.getUserAccountsByUserId(user.getId())).thenReturn(user.getAccounts());

        System.out.println(user);
        // Perform the request with the Authorization header
        ResultActions resultActions = mockMvc.perform(get("/api/user/my-accounts")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].accountNumber").value("AAFF12380 107"))
                .andExpect(jsonPath("$[0].accountType").value("PERSONAL"))
                .andExpect(jsonPath("$[0].accountStatus").value("ONBOARDING"))
                .andExpect(jsonPath("$[0].balance").value(100.0));

        // Print the response body for debugging
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Test response body: " + "/" + responseBody + "/");
    }
}