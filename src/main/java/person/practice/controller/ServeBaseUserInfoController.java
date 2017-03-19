package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import person.practice.model.BaseUserInfo;
import person.practice.model.UserInfo;
import person.practice.repository.UserInfoRepository;

/**
 * Created by Evan Hung on 2016/12/26.
 */
@RestController
public class ServeBaseUserInfoController {
    private UserInfoRepository userRepository;

    @Autowired
    public ServeBaseUserInfoController(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/user/{email}/info", method = RequestMethod.GET)
    public BaseUserInfo getBaseUserInfo(@PathVariable("email") String email) {
        UserInfo existUser = userRepository.findByEmail(email);
        BaseUserInfo serveUser = new BaseUserInfo();
        serveUser.setEmail(existUser.getEmail());
        serveUser.setUsername(existUser.getUsername());
        serveUser.setAvatar(existUser.getAvatar());
        serveUser.setTel(existUser.getTel());
        serveUser.setAddress(existUser.getAddress());
        return serveUser;
    }
}
