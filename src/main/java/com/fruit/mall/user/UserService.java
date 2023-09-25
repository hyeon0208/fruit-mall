package com.fruit.mall.user;

import com.fruit.mall.cart.CartRepository;
import com.fruit.mall.term.Term;
import com.fruit.mall.user.dto.UserInfoUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    public static final String COMMON = "일반";

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean myPageLoginCheck(Long userIdNo, String inputPwd) {
        String curPwd = userRepository.selectPwdById(userIdNo);
        if (curPwd == null || !passwordEncoder.matches(inputPwd, curPwd)) {
            return false;
        }
        return true;
    }

    public void insertUser(User user, Boolean term_flag5, Boolean term_flag6) {
        User joinUser = User.builder()
                .user_email(user.getUser_email())
                .user_name(user.getUser_name())
                .user_pwd(passwordEncoder.encode(user.getUser_pwd()))
                .role(Role.USER)
                .build();
        userRepository.insertUser(joinUser);

        Long user_id_no = joinUser.getUser_id_no();
        cartRepository.newUserCart(user_id_no);

        Integer termFlag5 = term_flag5 ? 1 : 0;
        Integer termFlag6 = term_flag6 ? 1 : 0;

        Term term5 = Term.builder()
                .user_id_no(user_id_no)
                .term_name("선택약관5")
                .term_flag(termFlag5)
                .build();

        Term term6 = Term.builder()
                .user_id_no(user_id_no)
                .term_name("선택약관6")
                .term_flag(termFlag6)
                .build();

        userRepository.insertTerm(term5);
        userRepository.insertTerm(term6);
    }

    public String selectEmailByUserEmail(String user_email) {
        return userRepository.selectEmailByUserEmail(user_email, COMMON);
    }

    public String selectUserNameByUserName(String user_name) {
        return userRepository.selectUserNameByUserName(user_name);
    }

    public void updateNewPassword(String user_email, String user_pwd) {
        userRepository.updateNewPassword(user_email, passwordEncoder.encode(user_pwd));
    }

    public void updateUserInfo(Long userIdNo, UserInfoUpdateDto dto) {
        String pwd = dto.getUserPwd();
        if (pwd != null && !pwd.equals("")) {
            dto.setUserPwd(passwordEncoder.encode(dto.getUserPwd()));
        }
        userRepository.updateUserInfo(userIdNo, dto);
    }
}
