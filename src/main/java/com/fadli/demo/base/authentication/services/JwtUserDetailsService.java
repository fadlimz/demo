package com.fadli.demo.base.authentication.services;

import com.fadli.demo.common.user.models.User;
import com.fadli.demo.common.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByBk(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found : " + user.getUserCode());
        }
        return new org.springframework.security.core.userdetails.User(user.getUserCode(), user.getPassword(),
                new ArrayList<>());
    }
}
