# Jwt Sample

- [x] CustomUserDetails
- [x] CustomUserDetailsService
- [x] @WithCustomUserDetails
- [x] @WithCustomUserDetailsSecurityContextFactory
- [x] RoleHierarchy
- [x] SecurityConfig
- [x] CORS
- [ ] JwtProvider
- [ ] AccessToken
- [ ] RefreshToken
- [ ] [@Pre and @Post Annotations](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#el-pre-post-annotations)
- [x] Jwt Authentication Using Cookies 
- [ ] Jwt Authentication Using Bearer Authorization
- [ ] OAuth2

## Role Hierarchy
권한 인가 과정
1. `FilterSecurityInterceptor`에서 `AccessDecisionManager`로 인가 처리 요청
2. `AccessDecisionManager`가 `AccessDecisionVoter`로 위임
3. `AccessDecisionVoter`의 구현체인 `RoleVoter`를 상속받는 `RoleHierarchyVoter`가 인가 여부 판단
4. `RoleHierarchyVoter`는 생성 될 때 `RoleHierarchy` 인자로 받음

```java
@Bean
public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    String hierarchy = "ROLE_ADMIN > ROLE_MANAGER > ROLE_USER";
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
}
```


## References

- [Spring Security – Roles and Privileges](https://www.baeldung.com/role-and-privilege-for-spring-security-registration)
- [Introduction to Spring Method Security](https://www.baeldung.com/spring-security-method-security)
- [A Custom Security Expression with Spring Security](https://www.baeldung.com/spring-security-create-new-custom-security-expression)
- [Spring Expression Language Guide](https://www.baeldung.com/spring-expression-language)
- [Topical Guide - Spring Security Architecture](https://spring.io/guides/topicals/spring-security-architecture)
- [Spring Security: Authentication and Authorization In-Depth](https://www.marcobehler.com/guides/spring-security)
- [React.js and Spring Data REST](https://spring.io/guides/tutorials/react-and-spring-data-rest/)
- [Spring Security Reference - Testing JWT Authentication](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#testing-jwt)
- [Spring Guide - Creating API Documentation with Restdocs](https://spring.io/guides/gs/testing-restdocs/)
- [Spring Reference - Authorization Architecture](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#authz-arch)

### JWT
- [Java JWT: JSON Web Token for Java and Android](https://github.com/jwtk/jjwt)
- [Stack Overflow - How to identify if the OAuth token has expired?](https://stackoverflow.com/questions/30826726/how-to-identify-if-the-oauth-token-has-expired)
- [Where to Store your JWTs – Cookies vs HTML5 Web Storage](https://stormpath.com/blog/where-to-store-your-jwts-cookies-vs-html5-web-storage)
- [Spring Security with JWT for REST API](https://www.toptal.com/spring/spring-security-tutorial)
- [Spring Security- JWT 로그인 구현하기 // 쿠키](https://emgc.tistory.com/133)