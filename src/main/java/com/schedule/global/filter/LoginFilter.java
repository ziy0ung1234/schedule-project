package com.schedule.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 로그인 여부를 검사하는 커스텀 서블릿 필터 클래스입니다.
 * <p>
 * 세션에 {@code userId}가 존재하지 않는 요청은 401 Unauthorized 응답을 반환하며,
 * 로그인 및 회원가입 요청은 예외적으로 필터를 통과시킵니다.
 * </p>
 *
 * <h2>필터 동작 방식</h2>
 * <ol>
 *   <li>요청 URI를 확인하여 {@link #EXCLUDED_URLS}에 포함된 경우 필터를 통과시킴</li>
 *   <li>그 외 요청은 세션 존재 여부와 {@code userId} 속성 확인</li>
 *   <li>세션이 없거나 userId가 없으면 401 Unauthorized 반환</li>
 *   <li>유효한 세션이면 다음 필터 체인으로 전달</li>
 * </ol>
 *
 * <h2>예외 경로</h2>
 * <ul>
 *   <li><b>/signup</b> – 회원가입 요청</li>
 *   <li><b>/signin</b> – 로그인 요청</li>
 * </ul>
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
