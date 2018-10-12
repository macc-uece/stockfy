package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);
}
