package com.muravlev.core.controllers;


import com.muravlev.core.dto.UserDTO;
import com.muravlev.core.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Юзеры")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(
            description = "Создаёт пользователя в базе данных",
            summary = "SUMMARY",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Не нашли",
                            responseCode = "404"
                    )
            }
    )
    @PostMapping("/create")
   // @CrossOrigin(origins = "*")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        System.out.println("Create user method called");
        UserDTO newUserDTO = service.createUser(userDTO);

        System.out.println("ENCODING1 =======================================================================");
        return new ResponseEntity<>(newUserDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/create", method = RequestMethod.OPTIONS)
    public ResponseEntity handleOptions() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = service.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO userDTO = service.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = service.updateUser(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long  id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable Long id) {
        return service.existsById(id);
    }
}
