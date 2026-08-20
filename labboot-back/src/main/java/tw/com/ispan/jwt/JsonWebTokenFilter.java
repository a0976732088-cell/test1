package tw.com.ispan.jwt;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JsonWebTokenFilter extends OncePerRequestFilter {
	private JsonWebTokenUtility jwtUtil;
	public JsonWebTokenFilter(JsonWebTokenUtility jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String method = request.getMethod();
		if("OPTIONS".equalsIgnoreCase(method) || "TRACE".equalsIgnoreCase(method) ||
				"CONNECT".equalsIgnoreCase(method)) {
			filterChain.doFilter(request, response);	//執行後續程式
			return;
		}
		
		String auth = request.getHeader("Authorization");
		if(auth!=null && auth.startsWith("Bearer ")) {
			String token = auth.substring(7);				//去掉前面的"Bearer "
			String subject = jwtUtil.validateToken(token);	//驗證token
			System.out.println("subject:"+subject);
			if(subject!=null) {
				//驗證成功，執行權限控管程式
				
				filterChain.doFilter(request, response);	//執行後續程式
				return;
			}
		}
		
		//403
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setHeader("Access-Control-Allow-Origin", "*");		
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		return;
	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
			throws ServletException {
		String uri = request.getRequestURI();
		
		return uri.startsWith("/ajax/secure/login") ||
				uri.startsWith("/pages/detail");
	}
}
