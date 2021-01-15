package curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.UsuarioModel;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long>{

	@Query("select u from UsuarioModel u where u.login = ?1")
	UsuarioModel findUserByLogin(String username);
	
	@Query("select u from UsuarioModel u where u.login like %?1% and u.login != 'Administrador'")
	List<UsuarioModel> findPessoaByName(String nome);
	
	@Query("select u from UsuarioModel u where u.login != 'Administrador'")
	List<UsuarioModel> findPessoaByTodos();
}