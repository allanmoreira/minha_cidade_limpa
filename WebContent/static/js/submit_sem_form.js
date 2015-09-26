function submit_sem_form(){
	var param1 = "";
	var param2 = "";
	
	
	$.ajax({
		url: 'url?param1='+param1+'&param2='+param2,
		// async: true,
		type: 'POST',
		dataType: 'json',
		data: {'submit':true},
		success: function(data){
			if(data.concluida) {
										
			}
			else {
				
			}
		}
	
	});
	return false;
}