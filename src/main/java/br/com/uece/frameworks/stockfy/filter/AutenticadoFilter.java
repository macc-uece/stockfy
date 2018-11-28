package br.com.uece.frameworks.stockfy.filter;

import br.com.uece.frameworks.stockfy.security.UserAutenticado;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Create by Bruno Barbosa - 28/11/2018
 */
@Component
@Order(1)
public class AutenticadoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            UserAutenticado userAutenticado = (UserAutenticado) ((Authentication) request.getUserPrincipal()).getPrincipal();
            String nomeAutenticado = userAutenticado.getNome();
            String cargoAutenticado = userAutenticado.getCargo();
            Long imagemAutenticado = userAutenticado.getImagem();
            servletRequest.setAttribute("nomeAutenticado", nomeAutenticado);
            servletRequest.setAttribute("cargoAutenticado", cargoAutenticado);
            servletRequest.setAttribute("imagemAutenticado", imagemAutenticado);
        } catch (Exception e) {}
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
