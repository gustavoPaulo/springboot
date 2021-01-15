package curso.springboot.model;

public enum SexoPessoa {

	NAO_INFORMADO("Não informar"),
	MASCULINO("Masculino"),
	FEMININO("Feminino");
	
	
	private String descricao;
	
	SexoPessoa(String descricao){
		
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		
		return descricao;
	}
}