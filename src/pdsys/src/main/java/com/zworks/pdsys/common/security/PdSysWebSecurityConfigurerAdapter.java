//package com.zworks.pdsys.common.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//import com.zworks.pdsys.common.enumClass.ROLE;
//
//@Configuration  
//@EnableGlobalMethodSecurity(prePostEnabled=true) //这个是为SecurityController.auth服务的
//public class PdSysWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter  {
//	@Autowired
//    private PdSysAccessDeniedHandler accessDeniedHandler;
//	
//	//密码加密
//	@Bean
//	PasswordEncoder PasswordEncoder() {
//		return new PdSysPasswordEncoder();
//	}
//   
//	@Bean  
//    UserDetailsService UserDetailsService(){  
//        return new PdSysUserDetailsService();  
//    }
//    
//// 	  1.首先当我们要自定义Spring Security的时候我们需要继承自WebSecurityConfigurerAdapter来完成，相关配置重写对应 方法即可。 
////    2.我们在这里注册CustomUserService的Bean，然后通过重写configure方法添加我们自定义的认证方式。 
////    3.在configure(HttpSecurity http)方法中，我们设置了登录页面，而且登录页面任何人都可以访问，然后设置了登录失败地址，也设置了注销请求，注销请求也是任何人都可以访问的。 
////    4.permitAll表示该请求任何人都可以访问，.anyRequest().authenticated(),表示其他的请求都必须要有权限认证。 
////    5.这里我们可以通过匹配器来匹配路径，比如antMatchers方法，假设我要管理员才可以访问admin文件夹下的内容，我可以这样来写：.antMatchers("/admin/**").hasRole("ROLE_ADMIN")，也可以设置admin文件夹下的文件可以有多个角色来访问，写法如下：.antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN","ROLE_USER") 
////    6.可以通过hasIpAddress来指定某一个ip可以访问该资源,假设只允许访问ip为210.210.210.210的请求获取admin下的资源，写法如下.antMatchers("/admin/**").hasIpAddress("210.210.210.210") 
////    7.更多的权限控制方式参看下表： 
////    access/anonymous/denyall fully Authenticated has AnyAuthority hasAnyRole has Authority hasRole permitAll rememberMe authenticated hasIpAddress
//    @Override  
//    protected void configure(HttpSecurity http) throws Exception {  
//        http.authorizeRequests()  
////                .antMatchers("/thy", "/f/**", "/form", "/b").permitAll()
////        		.antMatchers("/sys/master/**", "/sys/**").hasRole(ROLE.ADMIN)
//                .anyRequest().authenticated() //任何请求,登录后可以访问
//                .and()//Refused to display 'http://localhost:8080/pdsys/main.html' in a frame because it set 'X-Frame-Options' to 'deny'
//                	.headers().frameOptions().disable()//现存框架使用了iframe构造，所以避免上述错误，暂且disable
////                .and()
////	                //开启cookie保存用户数据
////	                .rememberMe()
////	                //设置cookie有效期
////	                .tokenValiditySeconds(60 * 60 * 24 * 7)
////	                //设置cookie的私钥
////	                .key("")
//                .and()
//	                .formLogin()
//	                	.loginPage("/login")
////	                	.successHandler(successHandler)
////	                	.failureHandler(authenticationFailureHandler)
//	//                	.loginProcessingUrl("login")
//	                	.usernameParameter("no")
//	                	.passwordParameter("password")
//	                	.defaultSuccessUrl("/index")
//	                	.failureUrl("/login?error")
//	                	.permitAll() //登录页面用户任意访问 
//                .and()
//                	.logout()
//                	.logoutUrl("/logout")
//                	.logoutSuccessUrl("/login?logout")
//                	.invalidateHttpSession(true)
//                	.permitAll()//注销行为任意访问 
//                .and()
//                	.exceptionHandling().accessDeniedHandler(accessDeniedHandler); 
//    }  
//  
//    @Override  
//    public void configure(WebSecurity web) throws Exception {  
//        web.ignoring().antMatchers( 
//        		"/css/**", 
//        		"/fonts/**", 
//        		"/icons/**", 
//        		"/js/**", 
//        		"/libs/**", 
//        		"/favicon.ico");  
//        //防止拦截css,js，image文件  
//    }  
//  
//    @Override  
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
//    	auth.userDetailsService(UserDetailsService()).passwordEncoder(PasswordEncoder());  
//        //登录验证，绑定自定义的UserDetailServiceHolder  
//    }  
//}
