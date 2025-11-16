package com.schedule.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 로그인 인증 필터.
 * <p>로그인·회원가입 요청을 제외한 모든 요청에 대해 세션 유효성을 검사한다.</p>
 */
@Component
public class LoginFilter implements Filter {
    //로그인/회원가입은 필터 X
    private static final List<String> EXCLUDED_URLS = List.of(
            "/signup", "/signin"
    );
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        // 로그인/회원가입 요청은 필터 통과
        if (isExcludedPath(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = httpRequest.getSession(false); // 세션이 없으면 null 반환
        if (session == null || session.getAttribute("userId") == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.getWriter().write("""
                {
                    "code": "UNAUTHORIZED",
                    "message": "로그인이 필요합니다."
                }
            """);
            return; // 여기서 Controller로 넘어가지 않음
        }
        chain.doFilter(request, response);
    }
    private boolean isExcludedPath(String uri) {
        return EXCLUDED_URLS.stream().anyMatch(uri::startsWith);
    }
}
