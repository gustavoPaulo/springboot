$('#confirmacaoExclusaoModalUsuario').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget);
	
	var idUsuario = button.data('id');
	var loginUsuario = button.data('login');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	if(!action.endsWith('/')){
		
		action += '/';
	}
	
	form.attr('action', action + idUsuario);
	
	modal.find('.modal-body span').html('Você deseja mesmo excluir o(a) <strong>' + loginUsuario + '</strong>? <br>'
										+ 'Esse usuário perderá acesso total ao sistema.');
	
});

$(function(){
	
	$('[rel="tooltip"]').tooltip();
	
});