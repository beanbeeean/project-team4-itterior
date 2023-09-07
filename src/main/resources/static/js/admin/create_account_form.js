function createAccountForm() {
	console.log('createAccountForm() CALLED!!');
	
	let form = document.create_account_form;

	if (form.a_id.value == '') {
		alert('아이디를 입력해주세요');
		form.a_id.focus();
		
	} else if (form.a_pw.value == '') {
		alert('패스워드를 입력해주세요');
		form.a_pw.focus();
		
	} else if (form.a_mail.value == '') {
		alert('메일을 입력해주세요');
		form.a_mail.focus();
		
	} else if (form.a_phone.value == '') {
		alert('연락처를 입력해주세요');
		form.a_phone.focus();
		
	} else {
		form.submit();
		
	}
	
}