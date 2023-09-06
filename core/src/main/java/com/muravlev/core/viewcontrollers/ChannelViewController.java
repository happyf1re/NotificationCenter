package com.muravlev.core.viewcontrollers;

import com.muravlev.core.dto.ChannelDTO;
import com.muravlev.core.dto.InvitationRequest;
import com.muravlev.core.dto.PostDTO;
import com.muravlev.core.services.ChannelService;
import com.muravlev.core.services.ChannelServiceImpl;
import com.muravlev.core.services.PostServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/channel")
public class ChannelViewController {

    private final ChannelServiceImpl channelService;
    private final PostServiceImpl postService;

    public ChannelViewController(ChannelServiceImpl channelService, PostServiceImpl postService) {
        this.channelService = channelService;
        this.postService = postService;
    }

    @GetMapping("/create")
    public String showCreateChannelPage(Model model) {
        model.addAttribute("channelDTO", new ChannelDTO());
        return "createChannel";
    }

    @PostMapping("/create")
    public String createChannel(ChannelDTO channelDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        channelDTO.setCreatorId(userId);
        channelService.createChannel(channelDTO);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/subscribe/{channelId}")
    public String subscribe(@PathVariable Long channelId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            channelService.subscribe(userId, channelId);
            return "redirect:/user/dashboard";
        } else {
            return "redirect:/user/login";
        }
    }

    @GetMapping("/unsubscribe/{channelId}")
    public String unsubscribe(@PathVariable Long channelId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            channelService.unsubscribe(userId, channelId);
            return "redirect:/user/dashboard";
        } else {
            return "redirect:/user/login";
        }
    }

    @GetMapping("/{channelId}")
    public String viewChannel(@PathVariable Long channelId, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        List<PostDTO> posts = postService.getAllPostsByChannelId(channelId);
        model.addAttribute("posts", posts);

        ChannelDTO channel = channelService.getChannelById(channelId);
        model.addAttribute("channel", channel);

        return "channelView";
    }

    @PostMapping("/{channelId}/addPost")
    public String addPost(@PathVariable Long channelId, @RequestParam String content, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        PostDTO newPost = new PostDTO();
        newPost.setChannelId(channelId);
        newPost.setAuthorId(userId);
        newPost.setContent(content);

        postService.createPost(newPost);

        return "redirect:/channel/" + channelId;
    }

    @GetMapping("/{channelId}/invite")
    public String showInvitePage(Model model, @PathVariable Long channelId) {
        return "invitePage";
    }

    @PostMapping("/{channelId}/invite")
    public String inviteUser(@ModelAttribute InvitationRequest request, RedirectAttributes redirectAttributes) {
        try {
            channelService.invite(request.getInviterId(), request.getInviteeId(), request.getChannelId());
            redirectAttributes.addFlashAttribute("message", "Invitation sent successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/invitations/{invitationId}/accept")
    public String acceptInvitation(@PathVariable Long invitationId, @RequestParam Long inviteeId, RedirectAttributes redirectAttributes) {
        try {
            channelService.acceptInvitation(inviteeId, invitationId);
            redirectAttributes.addFlashAttribute("message", "Invitation accepted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/invitations/{invitationId}/decline")
    public String declineInvitation(@PathVariable Long invitationId, @RequestParam Long inviteeId, RedirectAttributes redirectAttributes) {
        try {
            channelService.declineInvitation(inviteeId, invitationId);
            redirectAttributes.addFlashAttribute("message", "Invitation declined successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/user/dashboard";
    }

}
