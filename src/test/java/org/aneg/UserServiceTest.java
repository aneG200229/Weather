package org.aneg;

import jakarta.servlet.http.Cookie;
import org.aneg.config.TestAppConfig;
import org.aneg.dao.SessionDao;
import org.aneg.dao.UserDao;
import org.aneg.dto.user.UserLoginDto;
import org.aneg.dto.user.UserRegisterDto;
import org.aneg.exception.InvalidPasswordException;
import org.aneg.exception.NotFoundException;
import org.aneg.exception.PasswordMismatchException;
import org.aneg.exception.UserAlreadyExistsException;
import org.aneg.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class})
@Transactional
public class UserServiceTest{

    @Autowired
    private UserService service;

    @Autowired
    private UserDao dao;

    @Autowired
    private SessionDao sessionDao;

    @Test
    public void register_whenValidData_savesUserToDatabase(){

        UserRegisterDto user = new UserRegisterDto();
        user.setUsername("User1");
        user.setPassword("qwerty123");
        user.setRepeatPassword("qwerty123");

        service.register(user);

        assertTrue(dao.findByLogin(user.getUsername()).isPresent());

    }

    @Test
    public void register_whenLoginAlreadyExist_throwsUserAlreadyExistsException(){
        UserRegisterDto user1 = new UserRegisterDto();
        user1.setUsername("User1");
        user1.setPassword("qwerty123");
        user1.setRepeatPassword("qwerty123");

        UserRegisterDto user2 = new UserRegisterDto();
        user2.setUsername("User1");
        user2.setPassword("qwerty123");
        user2.setRepeatPassword("qwerty123");

        service.register(user1);

        assertThrows(UserAlreadyExistsException.class,() -> service.register(user2));
    }

    @Test
    public void register_whenPasswordsMismatch_throwsPasswordMismatchException(){
        UserRegisterDto user = new UserRegisterDto();
        user.setUsername("User1");
        user.setPassword("qwerty123");
        user.setRepeatPassword("qwerty122");

        assertThrows(PasswordMismatchException.class, () -> service.register(user));

    }

    @Test
    public void login_whenValidData_createSession(){
        MockHttpServletResponse response = new MockHttpServletResponse();
        UserRegisterDto userRegister = new UserRegisterDto();
        userRegister.setUsername("User1");
        userRegister.setPassword("qwerty123");
        userRegister.setRepeatPassword("qwerty123");

        UserLoginDto userLogin = new UserLoginDto();
        userLogin.setUsername("User1");
        userLogin.setPassword("qwerty123");

        service.register(userRegister);
        service.login(userLogin, response);
        Cookie cookie = response.getCookie("SESSION_ID");

        assertNotNull(cookie);
        assertTrue(sessionDao.findById(UUID.fromString(cookie.getValue())).isPresent());

    }

    @Test
    public void login_whenWrongPassword_throwsInvalidPasswordException(){
        MockHttpServletResponse response = new MockHttpServletResponse();
        UserRegisterDto userRegister = new UserRegisterDto();
        userRegister.setUsername("User1");
        userRegister.setPassword("qwerty123");
        userRegister.setRepeatPassword("qwerty123");

        UserLoginDto userLogin = new UserLoginDto();
        userLogin.setUsername("User1");
        userLogin.setPassword("qwerty111");

        service.register(userRegister);

        assertThrows(InvalidPasswordException.class, ()->service.login(userLogin,response));

    }

    @Test
    public void login_whenUserNotFound_throwsNotFoundException(){
        MockHttpServletResponse response = new MockHttpServletResponse();

        UserLoginDto userLogin = new UserLoginDto();
        userLogin.setUsername("User1");
        userLogin.setPassword("qwerty111");

        assertThrows(NotFoundException.class, ()->service.login(userLogin,response));
    }

}
