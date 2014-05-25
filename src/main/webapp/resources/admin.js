jQuery(function($){
	$('#searchForm').submit(function(e){
		e.preventDefault();
		if(!$(this).attr('action')){
			$(this).attr('action', window.location.href);
		}
		$.pjax.submit(e, '#tableContent');
	});
	$(document).on('click', '.page a[data-pjax]', function(e){
		e.preventDefault();
		var searchForm = $('#searchForm');
		if(searchForm.length > 0){
			searchForm.attr('action', $(this).attr('href')).submit();
		}else{
			$.pjax({
				container : '#tableContent', 
				url : $(this).attr('href')
			});
		}
	});
	$(document).on('change', '.page .jumpPage', function(){
		var val = $(this).val();
		if(!val || isNaN(val)){
			val = $(this).attr('data-origin');
		}
		var jumpPageLink = $('.page .jumpPageLink');
		jumpPageLink.attr('href', jumpPageLink.attr('data-base') + val).trigger('click');
	});
});
