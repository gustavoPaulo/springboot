package curso.springboot.model;

public enum TipoTelefone {

	CASA("Casa"),
	CELULAR("Celular"),
	RECADO("Recado"),
	SECUNDARIO("Secundário");
	
	
	private String descricao;
	
	TipoTelefone(String descricao){
		
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		
		return descricao;
	}

}