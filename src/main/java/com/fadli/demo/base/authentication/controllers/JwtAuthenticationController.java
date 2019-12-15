package com.fadli.demo.base.authentication.controllers;

import com.fadli.demo.base.authentication.config.JwtTokenUtil;
import com.fadli.demo.base.authentication.models.JwtRequest;
import com.fadli.demo.base.authentication.models.JwtResponse;
import com.fadli.demo.base.authentication.services.JwtUserDetailsService;
import com.fadli.demo.base.exceptions.BusinessException;
import com.fadli.demo.base.localThread.LocalErrors;
import com.fadli.demo.base.utils.PasswordUtil;
import com.fadli.demo.common.user.models.User;
import com.fadli.demo.common.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private JwtUserDetailsService jwtUserDetailsService;
    @Autowired private LocalErrors errorManager;
    @Autowired private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        User user = userService.findByBk(authenticationRequest.getUser());
        if (user == null) {
            errorManager.addError("authentication.user.not.found", authenticationRequest.getUser());
        }
        errorManager.throwBatchError();

        boolean valid = PasswordUtil.isPasswordValid(authenticationRequest.getPassword(), user.getPassword());
        if (valid) {
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUser());
            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            errorManager.addError("authentication.password.invalid");
        }
        errorManager.throwBatchError();

        return null;
    }
}
