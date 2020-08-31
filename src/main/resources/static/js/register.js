function onFormSubmission(form) {
	const id = form.querySelector("input[name=patientId]").value;
	const password = form.querySelector("input[name=password]").value;
	const name = form.querySelector("input[name=name]").value;
	const contact = form.querySelector("input[name=contact]").value;

	const birthdayValue = form.querySelector("input[name=birthday]").value;
	const birthday = isEmpty(birthdayValue) ? null : birthdayValue;

	const gcontact = form.querySelector("input[name=gcontact]").value;

	const sexElement = form.querySelector("input[name=gender]:checked");
	const sex = sexElement? sexElement.value: null;

	const body = {
		patientId: id,
		password: password,
		name: name,
		contact: contact,
		birthday: birthday,
		gender: sex,
		gcontact: gcontact
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

	if(isEmpty(removeSpace(id))){
		alert("아이디를 작성해주세요..");
	} else if (isEmpty(removeSpace(password))){
		alert("비밀번호를 작성해주세요.");
	} else if (isEmpty(removeSpace(name))){
		alert("이름을 작성해주세요");
	} else if (isEmpty(removeSpace(contact))){
		alert("연락처(본인)가 공백입니다.");
	} else if (isEmpty(removeSpace(gcontact))){
		alert("연락처(보호자)가 공백입니다.");
	}
	else {
		fetch(url, options)
		.then(response => response.json())
		.then(data => {
			console.log(data);
			if (data.code === 'OK') {
				window.location.href = "/home";
				alert("정상 적으로 회원 가입 되었습니다.");
			} else if (data.code === 'ERROR_DUPLICATE_ID'){
				alert("이미 존재하는 아이디 입니다.");
			} else if (data.code === 'ERROR_DUPLICATE_CONTACT'){
				alert("이미 같은 번호로 가입을 진행하셨습니다. (번호를 확인하시고, 사전에 가입하시지 않으셨다면 관리자에게 말씀해주세요.)");
			} else if (data.code === 'ERROR_DUPLICATE_GCONTACT'){
				alert("이미 같은 보호자분 번호로 가입을 진행하셨습니다. (번호를 확인하시고, 사전에 가입하지 않으셨다면 관리자에게 말씀해주세요.)")
			}else {
				alert("회원 가입 실패");
			}
		});
	}

	return false;
}
function onForGuardianSubmission(form) {
	const id = form.querySelector("input[name=guardianId]").value;
	const password = form.querySelector("input[name=gpassword]").value;
	const name = form.querySelector("input[name=gname]").value;
	const contact = form.querySelector("input[name=gtell]").value;
	const g_body = {
		guardianId: id,
		gpassword: password,
		gname: name,
		gtell: contact
	};
	console.log("1111111" + password);

	const url = form.action;
	const options = {
		method: "POST",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		},
		body: JSON.stringify(g_body)
	};

	console.log("22222" + password);

	if(isEmpty(removeSpace(id))){
		alert("아이디를 작성해주세요..");
	} else if (isEmpty(removeSpace(password))){
		alert("비밀번호를 작성해주세요.");
	} else if (isEmpty(removeSpace(name))){
		alert("이름을 작성해주세요");
	} else if (isEmpty(removeSpace(contact))){
		alert("연락처(본인)가 공백입니다.");
	}
	else {
		fetch(url, options)
			.then(response => response.json())
			.then(data => {
				console.log(data);
				if (data.code === 'OK') {
					window.location.href = "/home";
					alert("정상 적으로 회원 가입 되었습니다.");
				} else if (data.code === 'ERROR_DUPICATE_GID'){
					alert("이미 존재하는 아이디 입니다.");
				} else if (data.code === 'ERROR_DUPLICATE_GTELL'){
					window.location.href = "/home";
					alert("정상 적으로 회원 가입 되었습니다.");
				} else if (data.code === 'ERROR'){
					alert("ERROR");
					console.log(password);
				}
				else{
					alert("회원 가입 실패");
				}
			});
	}
	return false;
}

function isEmpty(value){
	if (value.length === 0) {
		return true;
	} else {
		return false;
	}
}

function removeSpace(value) {
	return value.replace(/\s/g,"");
}
