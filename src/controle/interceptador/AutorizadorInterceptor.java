package controle.interceptador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**Classe que monitora durante as requisições se o usuário está logado e, se não estiver, o redireciona para a página de login 
 * 
 */
public class AutorizadorInterceptor extends HandlerInterceptorAdapter {
	/*
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) throws Exception {
		
		String uri = request.getRequestURI();
		if (uri.endsWith("login") || uri.endsWith("efetuar-login") || uri.contains("static")) {
			return true;
		}

		if (request.getSession().getAttribute("usuarioLogado") != null) {
			return true;
		}

		response.sendRedirect("login");
		return false;
	}
	*/
}