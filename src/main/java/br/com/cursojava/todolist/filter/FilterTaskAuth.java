package br.com.cursojava.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.cursojava.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository iUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Capta a autenticação
        // Valida usuario e senha
        
        var authorization = request.getHeader("Authorization");
        var authEncode = authorization.substring("Basic".length()).trim();
        
        byte[] authDecode = Base64.getDecoder().decode(authEncode);

        var authString = new String(authDecode);

        String[] credentials = authString.split(":");

        String userName = credentials[0];
        String password = credentials[1];

        var user = this.iUserRepository.findByUsername(userName);

        if (user == null) {
            response.sendError(401); // Sem Autorização
        } else {
            var passwordVerifyer = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            
            if (passwordVerifyer.verified) {
                filterChain.doFilter(request, response); // Autorizado
            } else {
                response.sendError(401); // Sem Autorização
            }
        }

    }
}
