$('#confirmacaoExclusaoModalPessoa').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget);
	
	var idPessoa = button.data('id');
	var nomePessoa = button.data('nome');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	if(!action.endsWith('/')){
		
		action += '/';
	}
	
	form.attr('action', action + idPessoa);
	
	modal.find('.modal-body span').html('Você deseja mesmo excluir o(a) <strong>' + nomePessoa + '</strong>? <br>'
										+ 'Essa pessoa pode possuir telefones cadastrados, as informações serão perdidas.');
	
});

$(function(){
	
	$('[rel="tooltip"]').tooltip();
	
});


function setaIdadePessoa(){
	
	var aniversario = document.getElementById('dataNascimento').value;
	var anoInformado = aniversario.split('/')[2];
	var mesInformado = aniversario.split('/')[1];
	var diaInformado = aniversario.split('/')[0];

	var anoAtual = new Date().getFullYear();
	var mesAtual = new Date().getMonth() + 1;
	var data = new Date();
	var diaAtual = data.getDate();
	
	var result;
	
	
	if(anoInformado < anoAtual){
		
		if(mesInformado <= mesAtual){
		
			if(diaInformado <= diaAtual){
				
				result = (anoAtual - anoInformado);

				$('#idade').val(result);

			}else if(diaInformado >= diaAtual){

				result = (anoAtual - anoInformado) - 1;

				$('#idade').val(result);
			}

		}else{

			result = (anoAtual - anoInformado) - 1;

			$('#idade').val(result);
		}

	}else{
	
		alert('Insira uma data válida!');
	}
	
}

function mostrarSenha(){
	
	var senha = $('#senha');
	var olho= $("#olho");

	olho.mousedown(function() {
	  senha.attr("type", "text");
	});

	olho.mouseup(function() {
	  senha.attr("type", "password");
	});

	$( "#olho" ).mouseout(function() { 
	  $("#senha").attr("type", "password");
	});
}