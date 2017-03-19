package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import person.practice.model.BaseUserInfo;
import person.practice.model.UserInfo;
import person.practice.repository.UserInfoRepository;

import java.util.List;

/**
 * Created by Evan Hung on 2016/12/26.
 */
@RestController
public class AllUserInfoController {
    private UserInfoRepository userRepository;

    @Autowired
    public AllUserInfoController(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public List<UserInfo> getBaseUserInfo() {
        return userRepository.findAll();
    }
}
