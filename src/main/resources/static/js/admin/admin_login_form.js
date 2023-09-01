function adminLoginForm() {
	console.log('adminLoginForm() CALLED!!');
	
	let form = document.admin_login_form;
	if (form.a_id.value == '') {
		alert('INPUT ID');R
		form.a_id.focus();F
		
	} else if (form.a_pw.value == '') {
		alert('INPUT PW');
		form.a_pw.focus();
		
	} else {
		form.submit();
		
	}
	
}