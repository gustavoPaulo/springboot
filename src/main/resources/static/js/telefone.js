$('#confirmacaoExclusaoModalTelefone').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget);
	
	var idTelefone = button.data('id');
	var numeroTelefone = button.data('numero');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	if(!action.endsWith('/')){
		
		action += '/';
	}
	
	form.attr('action', action + idTelefone);
	
	modal.find('.modal-body span').html('Você deseja mesmo excluir este número <strong>' + numeroTelefone + '</strong>?');
	
});

$(function(){
	
	$('[rel="tooltip"]').tooltip();
	
});