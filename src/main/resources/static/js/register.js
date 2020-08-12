function onFormSubmission(form) {
	const id = form.querySelector("input[name=id]").value;
	const password = form.querySelector("input[name=password]").value;
	const name = form.querySelector("input[name=name]").value;
	const contact = form.querySelector("input[name=contact]").value;
	const birthdate = form.querySelector("input[name=birthdate]").value;
	const sex = form.querySelector("input[name=sex]:checked").value;
	/*const caregiver = form.querySelector("input[name=caregiver]").value;*/

	const body = {
		id: id,
		password: password,
		name: name,
		contact: contact,
		birthdate: birthdate,
		sex: sex
		/*caregiver: caregiver*/
	};

	const url = form.action;
	const options = {
		method: "POST",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		},
		body: JSON.stringify(body)
	};

	if(id.search(/\s/) != -1){
		alert("아이디가 공백입니다.");
	}/* else if (password.search(/\s/) !== -1){
		alert("비밀번호가 공백입니다.");
	} else if (name.search(/\s/) !== -1){
		alert("이름이 공백입니다.");
	} else if (contact.search(/\s/) !== -1){
		alert("연락처가 공백입니다.");
	}*/ else {
		fetch(url, options)
		.then(response => response.json())
		.then(data => {
			console.log(data);
			if (data.code == 'OK') {
				window.location.href = "/home";
				alert("정상 적으로 회원 가입 되었습니다.");
			} else if (data.code == 'ERROR_DUPLICATE_ID'){
				alert("이미 존재하는 아이디 입니다.");
			} else {
				alert("회원 가입 실패");
			}
		});
	}

	return false;
}
