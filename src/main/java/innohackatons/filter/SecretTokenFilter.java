package innohackatons.filter;

import innohackatons.api.exception.MissingOrIncorrectTokenException;
import innohackatons.configuration.ApplicationConfiguration;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import org.springframework.http.HttpStatus;

public class SecretTokenFilter implements Filter {
    private final String secretToken;

    public SecretTokenFilter(@NotNull ApplicationConfiguration configuration) {
        this.secretToken = configuration.token();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        var token = request.getHeader("Token");

        try {
            if (token == null || !token.equals(secretToken)) {
                throw new MissingOrIncorrectTokenException();
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (MissingOrIncorrectTokenException ex) {
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
    }
}
