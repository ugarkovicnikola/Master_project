package ef.master.faq.configuration;


import ef.master.faq.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomAuthenticationProvider authProvider;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider);
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/student/**")
        .hasRole("STUDENT")
        .antMatchers("/professor/**")
        .hasRole("PROFESSOR")
        .antMatchers("/office/**")
        .hasRole("OFFICE")
        .and()
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .disable();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().mvcMatchers(HttpMethod.POST , "/student")
        .mvcMatchers(HttpMethod.POST, "/professor")
        .mvcMatchers(HttpMethod.POST, "/office");
  }
}

