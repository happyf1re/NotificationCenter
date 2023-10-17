//package com.muravlev.core;
//
//import com.muravlev.core.controllers.ChannelController;
//import com.muravlev.core.controllers.PostController;
//import com.muravlev.core.controllers.UserController;
//import com.muravlev.core.dto.ChannelDTO;
//import com.muravlev.core.dto.InvitationDTO;
//import com.muravlev.core.dto.PostDTO;
//import com.muravlev.core.dto.UserDTO;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import java.util.Random;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//
//
//public class ApplicationSimulator {
//
//    private final UserController userController;
//    private final ChannelController channelController;
//    private final PostController postController;
//    private final ScheduledExecutorService executorService;
//    private final Random random = new Random();
//
//    public ApplicationSimulator(UserController userController, ChannelController channelController, PostController postController, ScheduledExecutorService executorService) {
//        this.userController = userController;
//        this.channelController = channelController;
//        this.postController = postController;
//        this.executorService = executorService;
//    }
//
//    public void simulate() {
//        for (long i = 1; i <= 100000; i++) {
//            final long userId = i;
//            executorService.schedule(() -> {
//                UserDTO user = new UserDTO();
//                user.setName("User" + userId);
//                user.setEmail("User" + userId + "@example.com");
//                user.setPassword("Password" + userId);
//                userController.createUser(user);
//            }, getGaussianDelay(), TimeUnit.MILLISECONDS);
//        }
//
//        for (long i = 1; i <= 10000; i++) {
//            final long channelId = i;
//            executorService.schedule(() -> {
//                if (userController.existsById(channelId)) {
//                    ChannelDTO channel = new ChannelDTO();
//                    channel.setName("Channel" + channelId);
//                    channel.setCreatorId(channelId);
//                    channelController.createChannel(channel);
//                }
//            }, getGaussianDelay(), TimeUnit.MILLISECONDS);
//        }
//
//        for (long i = 1; i <= 10000; i++) {
//            for (long j = 1; j <= 3000; j++) {
//                final long postId = j;
//                final long channelId = i;
//                executorService.schedule(() -> {
//                    if (channelController.existsById(channelId) && userController.existsById(postId)) {
//                        PostDTO post = new PostDTO();
//                        post.setContent("Post content " + postId);
//                        post.setAuthorId(postId);
//                        post.setChannelId(channelId);
//                        postController.createPost(post);
//                    }
//                }, getGaussianDelay(), TimeUnit.MILLISECONDS);
//            }
//        }
//
//        // Subscribe and unsubscribe
//        for (long i = 1; i <= 10000; i++) {
//            final long channelId = i;
//            for (long j = 1; j <= 100; j++) {
//                final long userId = j;
//                executorService.schedule(() -> {
//                    if (channelController.existsById(channelId) && userController.existsById(userId)) {
//                        if (random.nextBoolean()) {
//                            channelController.subscribe(userId, channelId);
//                        } else {
//                            channelController.unsubscribe(userId, channelId);
//                        }
//                    }
//                }, getGaussianDelay(), TimeUnit.MILLISECONDS);
//            }
//        }
//
//        // Invite and accept/decline invitations
//        for (long i = 1; i <= 10000; i++) {
//            final long channelId = i;
//            for (long j = 1; j <= 100; j++) {
//                final long userId = j;
//                executorService.schedule(() -> {
//                    if (channelController.existsById(channelId) && userController.existsById(userId)) {
//                        InvitationDTO invitationDto = new InvitationDTO();
//                        invitationDto.setInviterId(userId);
//                        invitationDto.setInviteeId(userId);
//                        invitationDto.setChannelId(channelId);
//                        channelController.invite(invitationDto);
//                        UserDTO userDto = new UserDTO();
//                        userDto.setId(userId);
//                        userDto.setName("User" + userId);
//                        userDto.setEmail("User" + userId + "@example.com");
//                        userDto.setPassword("Password" + userId);
//                        if (random.nextBoolean()) {
//                            channelController.acceptInvitation(userDto, channelId);
//                        } else {
//                            channelController.declineInvitation(userDto, channelId);
//                        }
//                    }
//                }, getGaussianDelay(), TimeUnit.MILLISECONDS);
//            }
//        }
//
//        executorService.shutdown();
//    }
//
//    private long getGaussianDelay() {
//        return Math.abs((long) (random.nextGaussian() * 1000));
//    }
//}