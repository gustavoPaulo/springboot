package curso.springboot.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
public class PessoaModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Nome deve ser preenchido!")
	private String nome;
	
	@NotBlank(message = "Sobrenome deve ser preenchido!")
	private String sobrenome;
	
	@Enumerated(EnumType.STRING)
	private SexoPessoa sexo;
	
	@NotBlank(message = "E-mail deve ser preenchido!")
	@Email(message = "Digite um e-mail valido!")
	private String email;
	
	@NotBlank(message = "Senha deve ser preenchida!")
	private String senha;
	
	@Min(value = 5, message = "Idade deve ser maior que 5 anos!")
	@Max(value = 120, message = "Idade deve ser menor que 120 anos!")
	private int idade;
	
	@NotBlank(message = "CEP deve ser preenchido!")
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;
	
	@OneToMany(mappedBy = "pessoaModel", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<TelefoneModel> telefones;
	
	@ManyToOne
	private ProfissaoModel profissao;
	
	@NotNull(message = "Data de nascimento é obrigatória.")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Lob
	private byte[] curriculo;
	
	private String nomeFileCurriculo;
	private String tipoFileCurriculo;
	
	
	
	public String getNomeFileCurriculo() {
		return nomeFileCurriculo;
	}
	public void setNomeFileCurriculo(String nomeFileCurriculo) {
		this.nomeFileCurriculo = nomeFileCurriculo;
	}
	public String getTipoFileCurriculo() {
		return tipoFileCurriculo;
	}
	public void setTipoFileCurriculo(String tipoFileCurriculo) {
		this.tipoFileCurriculo = tipoFileCurriculo;
	}
	public void setCurriculo(byte[] curriculo) {
		this.curriculo = curriculo;
	}
	public byte[] getCurriculo() {
		return curriculo;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public ProfissaoModel getProfissao() {
		return profissao;
	}
	public void setProfissao(ProfissaoModel profissao) {
		this.profissao = profissao;
	}
	public SexoPessoa getSexo() {
		return sexo;
	}
	public void setSexo(SexoPessoa sexo) {
		this.sexo = sexo;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSenha() {
		return senha;
	}
	public void setTelefones(List<TelefoneModel> telefones) {
		this.telefones = telefones;
	}
	public List<TelefoneModel> getTelefones() {
		return telefones;
	}
	
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public int getIdade() {
		return idade;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	
}