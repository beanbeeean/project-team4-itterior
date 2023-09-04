function createChannelForm() {
	console.log('createChannelForm() CALLED!!');
	
	let form = document.create_channel_form;

	if (form.yc_channel.value == '') {
		alert('INPUT CHANNEL');
		form.yc_channel.focus();
		
	} else if (form.yc_bring_cnt.value == '') {
		alert('INPUT BRING CNT');
		form.yc_bring_cnt.focus();
		
	} else if (form.yc_show_cnt.value == '') {
		alert('INPUT SHOW CNT');
		form.yc_show_cnt.focus();
		
	} else {
		form.submit();
		
	}
	
}