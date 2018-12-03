package com.djorquab.jarvis.technicaltest.web;

import com.djorquab.jarvis.technicaltest.dto.UserDTO;
import com.djorquab.jarvis.technicaltest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserViewController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView userInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof User) {
            return new ModelAndView("backoffice", authenticatedMap());
        }
        return new ModelAndView("login");
    }

    @GetMapping("/find/users")
    public ModelAndView findUsers() {
        return new ModelAndView("findUsers", authenticatedMap());
    }

    @GetMapping("/view/user")
    public ModelAndView viewUser(@RequestParam(name="username", required = false) String username,
                                 @RequestParam(name="email", required = false) String email) {
        Map<String,Object> map= authenticatedMap();
        if (username != null && !"".equals(username)) {
            map.put("userInfo", userService.getUserByUsername(username));
        } else if (email != null && !"".equals(email)) {
            map.put("userInfo", userService.getUserByEmail(email));
        }
        return new ModelAndView("userInfo", map);
    }

    @GetMapping("/edit/user/page")
    public ModelAndView editUser(@RequestParam(name="username", required = false) String username,
                                 @RequestParam(name="email", required = false) String email) {
        Map<String,Object> map= authenticatedMap();
        UserDTO user = null;
        if (username != null && !"".equals(username)) {
            user = userService.getUserByUsername(username);
        } else if (email != null && !"".equals(email)) {
            user = userService.getUserByEmail(email);
        }
        map.put("userToEdit", userService.dtoToEditor(user));
        return new ModelAndView("userEdit", map);
    }

    private Map<String, Object> authenticatedMap() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> map = new HashMap<>();
        if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            map.put("user", userService.getUserByUsername(user.getUsername()));
        }
        return map;
    }
}
