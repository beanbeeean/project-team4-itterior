function createAccountForm() {
	console.log('createAccountForm() CALLED!!');
	
	let form = document.create_account_form;
	if (form.u_id.value == '') {
		alert('INPUT ID');
		form.u_id.focus();
		
	} else if (form.u_pw.value == '') {
		alert('INPUT PW');
		form.u_pw.focus();
		
	} else if (form.u_mail.value == '') {
		alert('INPUT MAIL');
		form.u_mail.focus();
		
	} else if (form.u_phone.value == '') {
		alert('INPUT PHONE');
		form.u_phone.focus();
		
	} else if (form.u_zipcode.value == '') {
		alert('INPUT ZIP CODE');
		form.u_zipcode.focus();
		
	} else if (form.u_main_addr.value == '') {
		alert('INPUT ADDRESS1');
		form.u_main_addr.focus();
		
	} else if (form.u_detail_addr.value == '') {
		alert('INPUT ADDRESS2');
		form.u_detail_addr.focus();
		
	} else {
		form.submit();
		
	}
	
}