function userLoginForm() {
	console.log('userLoginForm() CALLED!!');
	
	let form = document.user_login_form;
	if (form.u_id.value == '') {
		alert('아이디를 입력해주세요');
		form.u_id.focus();
		
	} else if (form.u_pw.value == '') {
		alert('패스워드를 입력해주세요');
		form.u_pw.focus();
		
	} else {
		form.submit();
		
	}
	
}