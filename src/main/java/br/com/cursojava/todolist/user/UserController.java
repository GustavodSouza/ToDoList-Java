package br.com.cursojava.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserRepository iUserRepository;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UserModel userModel) {

        var user = iUserRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            // Mensagem de Erro
            // Status Code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(iUserRepository.save(userModel));
    }
}
