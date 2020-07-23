/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Isaia
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    UserDetailsService udServ;
    
    @Autowired
    public void configureGlobalInDB(AuthenticationManagerBuilder amb) throws Exception{
        amb.userDetailsService(udServ).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity sec) throws Exception{
        sec.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/edit-user").hasRole("ADMIN")
                .antMatchers("/blog-create").hasAnyRole("ADMIN","AUTHOR")
                .antMatchers("/blog-edit").hasAnyRole("ADMIN","AUTHOR")
                .antMatchers("/author/{name}").hasAnyRole("ADMIN","AUTHOR")
                .antMatchers("/static-page-edit").hasRole("ADMIN")
                .antMatchers("/static-page-add").hasRole("ADMIN")
                .antMatchers("/hashtags").hasRole("ADMIN")
                .antMatchers("/hashtag-edit").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/blogs").permitAll()
                .antMatchers("/tagged-blogs").permitAll()
                .antMatchers("/blog/{id}").permitAll()
                .antMatchers("/static-pages").permitAll()
                .antMatchers("/static-page/{id}").permitAll()
                .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()
                .anyRequest().hasAnyRole("ADMIN", "AUTHOR")
                .and().formLogin().loginPage("/login").failureUrl("/login?login_error=1").permitAll()
                .and().logout().logoutSuccessUrl("/blogs").permitAll();
    }
    
    
    
}
