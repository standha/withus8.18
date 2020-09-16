function onFormSubmission(form) {
	const userType = form.querySelector("input[name=user]:checked").value;

	const id = form.querySelector("input[name=id]").value;
	const password = form.querySelector("input[name=password]").value;
	const name = form.querySelector("input[name=name]").value;
	const contact = form.querySelector("input[name=contact]").value;

	const birthdateValue = form.querySelector("input[name=birthdate]").value;
	const birthdate = isEmpty(birthdateValue) ? null : birthdateValue;

	const sexElement = form.querySelector("input[name=gender]:checked");
	const sex = sexElement? sexElement.value: null;

	const caregiverValue = form.querySelector("input[name=caregiver]").value;
	const token = form.querySelector("input[name=appToken]").value;

	const user = {
		userId: null,
		password: null,
		name: null,
		contact: caregiverValue
	}
	const caregiver = isEmpty(caregiverValue) ? null : user;
	const progress = type(userType)? 0 : null;
	const level = type(userType)? 1 : null;
	const appToken = isEmpty(token) ? null : token;
	const body = {
		userId: id,
		password: password,
		name: name,
		contact: contact,
		birthdate: birthdate,
		gender: sex,
		type: userType,
		caregiver: caregiver,
		appToken:appToken,
		progress: progress,
		level: level
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
	console.log(options)
	if(isEmpty(removeSpace(id))){
		alert("아이디가 공백입니다.");
	} else if (isEmpty(removeSpace(password))){
		alert("비밀번호가 공백입니다.");
	} else if (isEmpty(removeSpace(name))){
		alert("이름이 공백입니다.");
	} else if (isEmpty(removeSpace(contact))){
		alert("연락처(본인)가 공백입니다.");
	} else {
		fetch(url, options)
		.then(response => response.json())
		.then(data => {
			console.log(data);
			if (data.code === 'OK') {
				window.location.href = "/home";
				alert("정상 적으로 회원 가입 되었습니다.");
			} else if (data.code === 'ERROR_DUPLICATE_ID'){
				alert("이미 존재하는 아이디 입니다.");
			} else if (data.code === 'ERROR_NO_EXIST_CAREGIVER'){
				alert("존재하지 않는 보호자 번호 입니다. (공백 입력 또는 보호자 등록 해주세요)");
			} else {
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

function doDisplay(user) {
	console.log(user)
	const elems = document.getElementsByClassName("patient-only");

	if (user === 'CAREGIVER') {
		for (let i = 0; i < elems.length; i++) {
			elems[i].style.display = 'none'
		}
	} else {
		for (let i = 0; i < elems.length; i++) {
			elems[i].style.display = 'grid'
		}
	}
}

function type(value){
	if(value == "PATIENT")
	{
		return true;
	}
	else
	{
		return false;
	}
}
