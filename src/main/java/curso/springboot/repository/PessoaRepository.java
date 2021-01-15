package curso.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.PessoaModel;
import curso.springboot.model.SexoPessoa;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {

	@Query("select p from PessoaModel p where p.nome like %?1%")
	List<PessoaModel> findPessoaByName(String nome);

	@Query("select p from PessoaModel p where p.nome like %?1% and p.sexo = ?2")
	List<PessoaModel> findPessoaByNameSexo(String nome, SexoPessoa sexo);
	
	
	default Page<PessoaModel> findPessoaByNamePage(String nome, Pageable pageable){
		
		PessoaModel pessoaModel = new PessoaModel();
		pessoaModel.setNome(nome);
		
		//Configurando a pesquisa para consultar por partes o nome no banco
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher
				("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<PessoaModel> example = Example.of(pessoaModel, exampleMatcher);
		
		Page<PessoaModel> pessoas = findAll(example, pageable);
		
		return pessoas;
	}
	
	default Page<PessoaModel> findPessoaByNameSexoPage(String nome, SexoPessoa sexo, Pageable pageable){
		
		PessoaModel pessoaModel = new PessoaModel();
		pessoaModel.setNome(nome);
		pessoaModel.setSexo(sexo);
		
		//Configurando a pesquisa para consultar por partes o nome no banco
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
				.withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("sexo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<PessoaModel> example = Example.of(pessoaModel, exampleMatcher);
		
		Page<PessoaModel> pessoas = findAll(example, pageable);
		
		return pessoas;
	}
	
	default Page<PessoaModel> findPessoaBySexoPage(SexoPessoa sexo, Pageable pageable){
		
		PessoaModel pessoaModel = new PessoaModel();
		pessoaModel.setSexo(sexo);
		
		//Configurando a pesquisa para consultar por partes o nome no banco
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher
				("sexo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		Example<PessoaModel> example = Example.of(pessoaModel, exampleMatcher);
		
		Page<PessoaModel> pessoas = findAll(example, pageable);
		
		return pessoas;
	}
	
}