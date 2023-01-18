package com.huigwon.sinbaljang.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration//환경설정 파일임을 알림
@EnableWebSecurity//모든 웹에 대한 요청이 스프링 시큐리티의 컨트롤 하에 있음을 알림
@EnableMethodSecurity(prePostEnabled = true)//@PreAuthorize 애너테이션이 동작하도록 함
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and();
		http.csrf().disable();
		http.authorizeHttpRequests().requestMatchers(				
				new AntPathRequestMatcher("/**")).permitAll()
		.and()//h2-console 제한 해제
		.csrf().ignoringRequestMatchers(
				new AntPathRequestMatcher("/h2-console/**"))
	.and()//h2 db 표시 해제
	.headers()
	.addHeaderWriter(new XFrameOptionsHeaderWriter(
			XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
			//로그인 로그아웃 설정
			.and()//로그인
				.formLogin()
				.loginPage("/login")//로그인 페이지가 보이게 하는 요청
				.defaultSuccessUrl("/home")//로그인 성공시 이동할 요청
			.and()//로그아웃
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//로그아웃 요청
				.logoutSuccessUrl("/home")//로그아웃 성공시 이동할 요청
				.invalidateHttpSession(true);//세션삭제 로그아웃
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
