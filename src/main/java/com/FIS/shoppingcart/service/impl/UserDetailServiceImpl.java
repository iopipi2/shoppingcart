package com.FIS.shoppingcart.service.impl;
import com.FIS.shoppingcart.dao.UserRepository;
import com.FIS.shoppingcart.entities.UserModel;
import com.FIS.shoppingcart.service.LoginService;
import com.FIS.shoppingcart.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = userRepository.findUserByEmail(username);


//        if(user == null) {
//            throw new UsernameNotFoundException("Không tìm thấy user");
//        }
//        return new UserModel(user);
//

        System.out.println("Accounttt= " + user.getUsername()+ " va " + user.getPassword());

        if (user == null) {
            throw new UsernameNotFoundException("User " //
                    + username + " was not found in the database");
        }

        // EMPLOYEE,MANAGER,..
        String role = user.getRole();

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        // ROLE_EMPLOYEE, ROLE_MANAGER
        GrantedAuthority authority = new SimpleGrantedAuthority(role);

        grantList.add(authority);
        boolean enabled = user.getEnabled();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

//        UserDetails userDetails= (UserDetails) new User(user.getUsername(),user.getPassword(),enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,grantList);
//        return userDetails;
        return new LoginService(user);

    }




}
