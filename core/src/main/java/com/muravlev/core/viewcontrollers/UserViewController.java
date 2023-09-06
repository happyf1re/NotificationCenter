package com.muravlev.core.viewcontrollers;

import com.muravlev.core.dto.ChannelDTO;
import com.muravlev.core.dto.InvitationDTO;
import com.muravlev.core.dto.UserDTO;
import com.muravlev.core.entities.Channel;
import com.muravlev.core.mappers.ChannelMapper;
import com.muravlev.core.services.ChannelServiceImpl;
import com.muravlev.core.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserViewController {

    private final UserService userService;
    private final ChannelServiceImpl channelService;
    private final ChannelMapper channelMapper;


    @Autowired
    public UserViewController(UserService userService, ChannelServiceImpl channelService, ChannelMapper channelMapper) {
        this.userService = userService;
        this.channelService = channelService;
        this.channelMapper = channelMapper;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "/login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        if (userService.authenticate(username, password)) {
            UserDTO user = userService.getUserByUsername(username); // предполагается, что такой метод существует
            session.setAttribute("userId", user.getId());
            return "redirect:/user/dashboard";
        } else {
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // имя HTML-шаблона Thymeleaf для страницы регистрации
    }

    @PostMapping("/register")
    public String handleRegistration(@RequestParam String username, @RequestParam String password, @RequestParam String email, Model model) {
        if (userService.existsByUsername(username) || userService.existsByEmail(email)) {
            model.addAttribute("errorMessage", "Username or email already exists");
            return "register";
        }

        UserDTO newUser = new UserDTO();
        newUser.setName(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        userService.createUser(newUser);

        return "redirect:/user/login";
    }

    @GetMapping("/dashboard")
    public String showUserDashboard(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        UserDTO user = userService.getUserById(userId);

        List<ChannelDTO> publicChannels = channelService.getAllPublicChannels();
        List<ChannelDTO> privateChannels = channelService.getAllPrivateChannels(userId);


        model.addAttribute("name", user.getName());
        model.addAttribute("publicChannels", publicChannels);
        model.addAttribute("privateChannels", privateChannels);

        return "dashboard";
    }

    @GetMapping("/subscriptions")
    public String showUserSubscriptions(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        List<ChannelDTO> userSubscriptions = userService.getUserSubscriptions(userId);
        model.addAttribute("userSubscriptions", userSubscriptions);
        return "subscriptions";
    }

    @GetMapping("/invitations")
    public String showUserInvitations(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        List<InvitationDTO> userInvitations = userService.getUserInvitations(userId);
        model.addAttribute("userInvitations", userInvitations);
        return "invitations";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }


}
