package curso.springboot.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import curso.springboot.model.UsuariosRole;


public interface UsuariosRoleRepository extends CrudRepository<UsuariosRole, Long>{

	@Query("select u from UsuariosRole u where u.usuario_id != 1")
	List<UsuariosRole> findAllPermissoes();
}