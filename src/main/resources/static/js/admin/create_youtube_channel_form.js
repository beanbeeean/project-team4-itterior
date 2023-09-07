function createChannelForm() {
	console.log('createChannelForm() CALLED!!');
	
	let form = document.create_channel_form;

	if (form.yc_channel.value == '') {
		alert('채널을 입력해주세요.');
		form.yc_channel.focus();
		
	} else if (form.yc_bring_cnt.value == '') {
		alert('가져올 영상의 갯수를 입력해주세요.');
		form.yc_bring_cnt.focus();
		
	} else if (form.yc_show_cnt.value == '') {
		alert('승인 여부를 선택해주세요.');
		form.yc_show_cnt.focus();
		
	} else {
		form.submit();
		
	}
	
}