package curso.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.ProfissaoModel;

@Repository
@Transactional
public interface ProfissaoRepository extends CrudRepository<ProfissaoModel, Long>{

	
}