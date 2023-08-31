package com.fruit.mall.user;

import com.fruit.mall.config.SessionUser;
import com.fruit.mall.term.Term;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean loginCheck(String pwd, User loginUser) {
        if (loginUser == null || !passwordEncoder.matches(pwd, loginUser.getUser_pwd())) {
            return false;
        }
        return true;
    }

    public Long getUserIdFromSession(SessionUser sessionUser) {
        if (sessionUser != null) {
            return userRepository.selectUserIdNByEmail(sessionUser.getEmail());
        }
        return null;
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    public User selectUserByUserEmail(String user_email) {
        return userRepository.selectUserByUserEmail(user_email);
    }

    public void insertTerm(Term term) {
        userRepository.insertTerm(term);
    }

    public String selectEmailByUserEmail(String user_email) {
        return userRepository.selectEmailByUserEmail(user_email);
    }

    public String selectUserNameByUserName(String user_name) {
        return userRepository.selectUserNameByUserName(user_name);
    }

    public Long selectUserIdNByEmail(String email) {
        return userRepository.selectUserIdNByEmail(email);
    }

    public void updateNewPassword(String user_email, String user_pwd) {
        userRepository.updateNewPassword(user_email, passwordEncoder.encode(user_pwd));
    }
}
