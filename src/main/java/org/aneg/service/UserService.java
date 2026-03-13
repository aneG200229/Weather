package org.aneg.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aneg.dao.UserDao;
import org.aneg.dto.user.UserLoginDto;
import org.aneg.dto.user.UserRegisterDto;
import org.aneg.exception.InvalidPasswordException;
import org.aneg.exception.PasswordMismatchException;
import org.aneg.exception.UserAlreadyExistsException;
import org.aneg.exception.NotFoundException;
import org.aneg.mapper.UserMapper;
import org.aneg.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDao dao;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;
    private final UserMapper mapper;

    @Transactional
    public void register(UserRegisterDto dto) {
        if (dao.findByLogin(dto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        if (!dto.getPassword().equals(dto.getRepeatPassword())) {
            throw new PasswordMismatchException("passwords must be the same");
        }
        User user = mapper.toUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        dao.save(user);
        log.info("User is registered: {}", user.getLogin());
    }

    @Transactional
    public void login(UserLoginDto dto, HttpServletResponse response) {
        User user = dao.findByLogin(dto.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.warn("Invalid password for the user: {}", user.getLogin());
            throw new InvalidPasswordException("Invalid password");
        }
        sessionService.createSession(user, response);
        log.info("Successful login: {}", user.getLogin());

    }
}
