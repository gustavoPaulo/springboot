package curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.TelefoneModel;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<TelefoneModel, Long> {
	
	@Query("select t from TelefoneModel t where t.pessoaModel.id = ?1")
	public List<TelefoneModel> getTelefoneModel(Long idPessoa);
}