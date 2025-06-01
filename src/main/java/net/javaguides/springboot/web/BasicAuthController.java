package net.javaguides.springboot.web;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.web.dto.AuthRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:4200")
public class BasicAuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(BasicAuthController.class);
    public BasicAuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public void login(@RequestBody AuthRequestDto authRequest) {

        logger.info("Login with username: " + authRequest.getUsername());
        try {
            Authentication authentication = authenticationManager
                    .authenticate( new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                            authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("User '{}' is connected", authRequest.getUsername());
        } catch (Exception e){
            logger.error("Error authentication", e);
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> searchByEmail(@RequestParam String email) {

        logger.info("Start search user by email: " + email);
        try {
            User user = userService.findByEmail(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e){
            logger.error("Error searching user by email", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
