function adminLoginForm() {
	console.log('adminLoginForm() CALLED!!');
	
	let form = document.admin_login_form;
	if (form.a_id.value == '') {
		alert('아이디를 입력해주세요.');
		form.a_id.focus();
		
	} else if (form.a_pw.value == '') {
		alert('패스워드를 입력해주세요.');
		form.a_pw.focus();
		
	} else {
		form.submit();
		
	}
	
}