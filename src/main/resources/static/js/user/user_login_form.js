function userLoginForm() {
	console.log('userLoginForm() CALLED!!');
	
	let form = document.user_login_form;
	if (form.u_id.value == '') {
		alert('INPUT ID');
		form.u_id.focus();
		
	} else if (form.u_pw.value == '') {
		alert('INPUT PW');
		form.u_pw.focus();
		
	} else {
		form.submit();
		
	}
	
}