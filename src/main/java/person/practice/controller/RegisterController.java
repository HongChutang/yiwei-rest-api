package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import person.practice.model.UserInfo;
import person.practice.repository.UserInfoRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Evan Hung on 2016/12/26.
 */
@RestController
public class RegisterController {
    private UserInfoRepository userRepository;

    @Autowired
    public RegisterController(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = {
            "Content-Type=application/json"})
    public void register(@RequestBody UserInfo user) {
        // 查找该邮箱是否已被使用
        UserInfo existUser = userRepository.findByEmail(user.getEmail());
        if (existUser != null) {
            throw new IllegalArgumentException("邮箱已被注册!");
        }
        //哈希加密存储
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @ExceptionHandler
    public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
