package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import person.practice.helper.GeneImgHelper;
import person.practice.model.BaseUserInfo;
import person.practice.model.UserInfo;
import person.practice.repository.UserInfoRepository;

/**
 * Created by Evan Hung on 2016/12/26.
 */
@RestController
public class UpdateUserInfoController {
    private UserInfoRepository userRepository;

    @Autowired
    public UpdateUserInfoController(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/user1/{email}/info", method = RequestMethod.PUT)
    public void updateUserInfo(@PathVariable("email") String email, @RequestBody BaseUserInfo baseUserInfo) {
        UserInfo existUserInfo = userRepository.findByEmail(email);
        if (!baseUserInfo.getUsername().equals(existUserInfo.getUsername())) {
            existUserInfo.setUsername(baseUserInfo.getUsername());
        }
        if (baseUserInfo.getAddress() != null && !baseUserInfo.getAddress().equals(existUserInfo.getAddress())) {
            existUserInfo.setAddress(baseUserInfo.getAddress());
        }
        if (baseUserInfo.getTel() != null && !baseUserInfo.getTel().equals(existUserInfo.getTel())) {
            existUserInfo.setTel(baseUserInfo.getTel());
        }
        if (baseUserInfo.getAvatar() != null) {
            if (!baseUserInfo.getAvatar().equals(existUserInfo.getAvatar())) {
                existUserInfo.setAvatar(GeneImgHelper.generateImage(baseUserInfo.getAvatar()));
            }
        }
        userRepository.save(existUserInfo);
    }
}
