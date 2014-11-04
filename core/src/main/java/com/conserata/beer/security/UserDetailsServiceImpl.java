package com.conserata.beer.security;

import com.google.common.base.Preconditions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Preconditions.checkNotNull(username);
        String[] roles = new String[]{"USER"};
        final List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(roles);
        return new User(username,username,auths);
    }
}
