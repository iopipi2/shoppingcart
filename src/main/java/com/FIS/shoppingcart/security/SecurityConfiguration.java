package com.FIS.shoppingcart.security;

import com.FIS.shoppingcart.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

 @Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailServiceImpl userDetailsService;
//	 @Autowired
//	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//		 // Setting Service to find User in the database.
//		 // And Setting PassswordEncoder
//		 auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//
//	 }



	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//phân quyền

		http.csrf().disable();
		http.authorizeRequests().antMatchers("/admin/cart", "/admin/product")//
				.access("hasAnyRole('ROLE_ADMIN')");

		//Cấu hình form login
		http.authorizeRequests().
				antMatchers("/**").permitAll()
				.and()
				.formLogin()//
				//
				.loginProcessingUrl("/login") // Submit URL
				.loginPage("/cai-nay-thu")//
				.defaultSuccessUrl("/trang-chu")//
				.failureUrl("/cai-nay-thu?error=true")//
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll();



//		http.csrf().disable()
//			.authorizeRequests()
//			.antMatchers("/admin/**").hasAnyRole("ADMIN")
//
//			.antMatchers("/**").hasAnyRole("USER","ADMIN").anyRequest().permitAll()
//			//cấu hình giao diện xác thực
//			.and()
////			.addFilterBefore(beforeAuthenticationFilter, BeforeAuthenticationFilter.class)
//			.formLogin().permitAll()
//			.loginPage("/cai-nay-thu").usernameParameter("username").loginProcessingUrl("/login").defaultSuccessUrl("/trang-chu")
//				.failureUrl("/cai-nay-thu?error=true")
//				.usernameParameter("username")
////				.passwordParameter("password")
//				.permitAll()
//			.and()
//			.logout()
//				.logoutSuccessUrl("/login")
//				.invalidateHttpSession(true)
//				.deleteCookies("JSESSIONID")
//		.permitAll();



//			.userInfoEndpoint().userService(oAuth2UserService)
//			.successHandler(oAuth2LoginSuccessHandler);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}

	 @Bean
	 public BCryptPasswordEncoder passwordEncoder() {
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		 return encoder;
	 }

	 @Override
	 protected void configure(AuthenticationManagerBuilder auth)
			 throws Exception {
		 auth.userDetailsService(userDetailsService) // Cung cáp userservice cho spring security
				 .passwordEncoder(passwordEncoder()); // cung cấp password encoder
		 String pw= passwordEncoder().encode("123456");
//																		 $2a$10$Km6PlinGSHE0CsdWIIuql.7WrZDVFxbp3bt49k0Nhnw1/OHMNIyFe
	 }

	//Trả về bean authenticationManager theo mặc định
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/*private CustomLoginFilter getCustomLoginFilter() throws Exception {
		CustomLoginFilter filter = new CustomLoginFilter("/login", "POST");
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
				if(!response.isCommitted()){
					response.sendRedirect("dang-nhap?error");
				}
			}
		});

		return filter;
	}*/
}
//