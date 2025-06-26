package com.medicolab.gateway.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * Intercepte les requêtes HTTP pour extraire, valider
 * un token JWT et établir le contexte de sécurité si l'utilisateur est authentifié.
 */
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final MapReactiveUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, MapReactiveUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Intercepte chaque requête HTTP, extrait le JWT depuis l’en-tête Authorization,
     * le valide, puis injecte l’authentification dans le contexte de sécurité réactif si possible.
     *
     * @param exchange l'échange HTTP WebFlux (requête + réponse)
     * @param chain la chaîne des filtres à exécuter
     * @return un flux réactif continuant la chaîne de traitement
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String jwt = authHeader.substring(7);

        String username;
        try {
            username = jwtUtil.getUsernameFromToken(jwt);
        } catch (Exception e) {
            return chain.filter(exchange);
        }

        if (username == null) {
            return chain.filter(exchange);
        }

        return userDetailsService.findByUsername(username)
                .flatMap(userDetails -> {
                    if (jwtUtil.validateToken(jwt)) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(
                                        Mono.just(new SecurityContextImpl(auth))
                                ));
                    } else {
                        return chain.filter(exchange);
                    }
                })
                .switchIfEmpty(chain.filter(exchange));
    }
}



