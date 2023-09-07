function createAccountForm() {
	console.log('createAccountForm() CALLED!!');
	
	let form = document.create_account_form;
	if (form.u_id.value == '') {
		alert('아이디를 입력해주세요');
		form.u_id.focus();
		
	} else if (form.u_pw.value == '') {
		alert('패스워드를 입력해주세요');
		form.u_pw.focus();
		
	} else if (form.u_mail.value == '') {
		alert('메일을 입력해주세요');
		form.u_mail.focus();
		
	} else if (form.u_phone.value == '') {
		alert('연락처를 입력해주세요');
		form.u_phone.focus();
		
	} else if (form.u_zipcode.value == '') {
		alert('우편번호를 입력해주세요');
		form.u_zipcode.focus();
		
	} else if (form.u_main_addr.value == '') {
		alert('도로명 주소를 입력해주세요');
		form.u_main_addr.focus();
		
	} else if (form.u_detail_addr.value == '') {
		alert('상세주소를 입력해주세요');
		form.u_detail_addr.focus();
		
	} else {
		form.submit();
		
	}
	
}