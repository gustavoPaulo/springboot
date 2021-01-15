function setaListaEmail(){

	var listaEmail = document.getElementById('listaEmailModal').value;

	document.getElementById('enviarPara').value = listaEmail;
	
	
	document.getElementById('pesquisar').action = "/pesquisarEmail";
	document.getElementById('pesquisar').method = 'get';
}