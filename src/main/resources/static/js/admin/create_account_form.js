function createAccountForm() {
	console.log('createAccountForm() CALLED!!');
	
	let form = document.create_account_form;

	if (form.a_id.value == '') {
		alert('INPUT ID');
		form.a_id.focus();
		
	} else if (form.a_pw.value == '') {
		alert('INPUT PW');
		form.a_pw.focus();
		
	} else if (form.a_mail.value == '') {
		alert('INPUT MAIL');
		form.a_mail.focus();
		
	} else if (form.a_phone.value == '') {
		alert('INPUT PHONE');
		form.a_phone.focus();
		
	} else {
		form.submit();
		
	}
	
}