function createAccountForm() {
	console.log('createAccountForm() CALLED!!');
	
	let form = document.create_account_form;
	if (form.m_id.value == '') {
		alert('INPUT ID');
		form.m_id.focus();
		
	} else if (form.m_pw.value == '') {
		alert('INPUT PW');
		form.m_pw.focus();
		
	} else if (form.m_mail.value == '') {
		alert('INPUT MAIL');
		form.m_mail.focus();
		
	} else if (form.m_phone.value == '') {
		alert('INPUT PHONE');
		form.m_phone.focus();
		
	} else if (form.m_zipcode.value == '') {
		alert('INPUT ZIP CODE');
		form.m_zipcode.focus();
		
	} else if (form.m_main_addr.value == '') {
		alert('INPUT ADDRESS1');
		form.m_main_addr.focus();
		
	} else if (form.m_detail_addr.value == '') {
		alert('INPUT ADDRESS2');
		form.m_detail_addr.focus();
		
	} else {
		form.submit();
		
	}
	
}