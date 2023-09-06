//package com.muravlev.core.viewcontrollers;
//
//import com.muravlev.core.dto.InvitationRequest;
//import com.muravlev.core.services.ChannelService;
//import com.muravlev.core.services.ChannelServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class InvitationViewController {
//    private final ChannelServiceImpl channelService;
//
//    @Autowired
//    public InvitationViewController(ChannelServiceImpl channelService) {
//        this.channelService = channelService;
//    }
//
//    @GetMapping("/{channelId}/invite")
//    public String showInvitePage(Model model, @PathVariable Long channelId) {
//        return "invitePage";
//    }
//
//    @PostMapping("/{channelId}/invite")
//    public String inviteUser(@ModelAttribute InvitationRequest request, RedirectAttributes redirectAttributes) {
//        try {
//            channelService.invite(request.getInviterId(), request.getInviteeId(), request.getChannelId());
//            redirectAttributes.addFlashAttribute("message", "Invitation sent successfully!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/dashboard/" + request.getChannelId();
//    }
//
//    @GetMapping("/invitations/{invitationId}/accept")
//    public String acceptInvitation(@PathVariable Long invitationId, @RequestParam Long inviteeId, RedirectAttributes redirectAttributes) {
//        try {
//            channelService.acceptInvitation(inviteeId, invitationId);
//            redirectAttributes.addFlashAttribute("message", "Invitation accepted successfully!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/dashboard";
//    }
//
//    @GetMapping("/invitations/{invitationId}/decline")
//    public String declineInvitation(@PathVariable Long invitationId, @RequestParam Long inviteeId, RedirectAttributes redirectAttributes) {
//        try {
//            channelService.declineInvitation(inviteeId, invitationId);
//            redirectAttributes.addFlashAttribute("message", "Invitation declined successfully!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/dashboard";
//    }
//}
