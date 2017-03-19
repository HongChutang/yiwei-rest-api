package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import person.practice.helper.JwtHelper;
import person.practice.model.AccessToken;
import person.practice.model.LoginEntity;
import person.practice.model.UserInfo;
import person.practice.repository.UserInfoRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Evan Hung on 2016/12/14.
 */
@RestController
public class LoginController {
    private UserInfoRepository userRepository;

    @Autowired
    public LoginController(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, headers = {
            "Content-Type=application/json"})
    public Object login(@RequestBody LoginEntity loginEntity) {
        UserInfo user = userRepository.findByEmail(loginEntity.getEmail());
        if (!new BCryptPasswordEncoder().matches(loginEntity.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("邮箱或密码错误!");
        }
        //拼装accessToken
        String accessToken = JwtHelper.createJWT(String.valueOf(user.getId()), user.getEmail(),
                 "audienceStr", "issuerStr", 86400*1000, "base64SecurityStr");

        //返回accessToken
        AccessToken accessTokenEntity = new AccessToken();
        accessTokenEntity.setAccess_token(accessToken);
        accessTokenEntity.setExpires_in(86400);
        accessTokenEntity.setToken_type("bearer");
        return accessTokenEntity;
    }

    @ExceptionHandler
    public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
