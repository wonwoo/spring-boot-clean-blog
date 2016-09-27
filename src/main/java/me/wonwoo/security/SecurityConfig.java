package me.wonwoo.security;

import lombok.RequiredArgsConstructor;
import me.wonwoo.github.GithubClient;
import me.wonwoo.github.GithubUser;
import me.wonwoo.user.User;
import me.wonwoo.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@Configuration
@EnableOAuth2Sso
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers(HttpMethod.GET, "/posts/new").hasRole("ADMIN")
      .antMatchers(HttpMethod.GET, "/posts/{id}").permitAll()
      .antMatchers("/posts/**").hasRole("ADMIN")
      .antMatchers("/categories/**").hasRole("ADMIN")
      .antMatchers("/", "/js/**", "/vendor/**", "/codemirror/**", "/markdown/**", "/login/**", "/css/**", "/img/**", "/webjars/**").permitAll()
      .anyRequest().authenticated()
      .and()
      .csrf()
      .and()
      .formLogin()
      .loginPage("/login")
      .permitAll()
      .and()
      .logout()
      .logoutSuccessUrl("/")
      .permitAll()
      .and()
      .headers()
      .frameOptions().sameOrigin();
  }

  @Bean
  public AuthoritiesExtractor authoritiesExtractor() {
    return map -> {
      String username = (String) map.get("login");
      if ("wonwoo".contains(username)) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
      } else {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
      }
    };
  }

  @Bean
  public PrincipalExtractor principalExtractor(GithubClient githubClient, UserRepository userRepository) {
    return map -> {
      String githubLogin = (String) map.get("login");
      User loginUser = userRepository.findByGithub(githubLogin);
      if (loginUser == null) {
        logger.info("Initialize user with githubId {}", githubLogin);
        GithubUser user = githubClient.getUser(githubLogin);
        loginUser = new User(user.getEmail(), user.getName(), githubLogin, user.getAvatar());
        userRepository.save(loginUser);
      }
      return loginUser;
    };
  }
}