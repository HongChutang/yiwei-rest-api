package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import person.practice.helper.JwtHelper;
import person.practice.model.AccessToken;
import person.practice.model.Admin;
import person.practice.repository.AdminRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Evan Hung on 2016/12/26.
 */
@RestController
public class AdminLoginController {
    private AdminRepository adminRepository;

    @Autowired
    public AdminLoginController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @RequestMapping(value = "/admin/register", method = RequestMethod.GET)
    public void register() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("12345678");
        //哈希加密存储
        admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
        adminRepository.save(admin);
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST, headers = {
            "Content-Type=application/json"})
    public Object login(@RequestBody Admin admin) {
        Admin existAdmin = adminRepository.findByUsername(admin.getUsername());
        if (!new BCryptPasswordEncoder().matches(admin.getPassword(), existAdmin.getPassword())) {
            throw new IllegalArgumentException("用户名或密码错误!");
        }
        //拼装accessToken
        String accessToken = JwtHelper.createJWT(String.valueOf(existAdmin.getId()), existAdmin.getUsername(),
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
