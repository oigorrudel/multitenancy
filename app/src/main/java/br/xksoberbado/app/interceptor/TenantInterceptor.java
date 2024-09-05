package br.xksoberbado.app.interceptor;

import br.xksoberbado.app.config.TenantHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    @SneakyThrows
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        final var tenantId = request.getHeader("X-Tenant-ID");

        TenantHolder.set(
            tenantId.equals("1") ? "MULTI_ONE" : "MULTI_TWO"
        );

        return true;
    }
}
