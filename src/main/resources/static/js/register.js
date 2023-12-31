function onFormSubmission(form) {
	let userType = "";
	switch (document.getElementById('checkType').className){
		case "patient active":
			userType = "PATIENT";
			break;
		case "patient":
			userType = "CAREGIVER";
			break;
		case "":
			alert("보호자와 환자 중 타입을 한가지 설정해 주세요.");
			break;
		default:
			alert("보호자와 환자 중 타입을 한가지 설정해 주세요.");
			break;
	}

	const id = form.querySelector("input[name=id]").value;
	const password = form.querySelector("input[name=password]").value;
	const name = form.querySelector("input[name=name]").value;
	const contact = form.querySelector("input[name=contact]").value;
	const height = form.querySelector("input[name=height]").value;
	//const userType = form.querySelector("input[name=user]:checked").value;
	/*const userType = document.querySelector(".PATIENT").dataset.value;*/

	const yearValue = form.querySelector("select[name=year]").value;
	let monthValue = form.querySelector("select[name=month]").value;
	if(monthValue < 10){
		monthValue = "0" + String(monthValue);
	}
	let dayValue = form.querySelector("select[name=day]").value;
	if(dayValue < 10){
		dayValue = "0" + String(dayValue);
	}
	const birthdateValue =new Date(yearValue + '-' + monthValue + '-' + dayValue);
	const birthdate = isEmpty(birthdateValue) ? null : birthdateValue;

	const sexElement = form.querySelector("input[name=gender]:checked");
	const sex = sexElement? sexElement.value: null;

	//const relativeElement = form.querySelector("select[name=relative]:checked");
	//const relative = relativeElement ? relativeElement.value : null;

	const selectElement = document.getElementById("relativeSelect");
	const relative = selectElement ? selectElement.value.toUpperCase() : null;

	//const patientValue = form.querySelector("input[name=patient]").value;
	const caregiverValue = form.querySelector("input[name=caregiver]").value;
	const patientValue = form.querySelector("input[name=patient]").value;

	var token = form.querySelector("input[name=appToken]").value;
	const caregiver_user = {
		userId: null,
		password: null,
		name: null,
		birthdate : null,
		height : null,
		contact: caregiverValue
	}
	const patient_user = {
		userId: null,
		password: null,
		name: null,
		birthdate : null,
		height : null,
		contact: patientValue
	}

	const patient = isEmpty(patientValue) ? null : patient_user;
	const caregiver = isEmpty(caregiverValue) ? null : caregiver_user;

	//const week = type(userType)? 0 : null;
	//const level = type(userType)? 1 : null;
	const week = 0;
	const level = 1;

	const appToken = isEmpty(token) ? null : token;
	let body = {
		userId: id,
		password: password,
		name: name,
		height : height,
		contact: contact,
		birthdate: birthdate,
		gender: sex,
		type: userType,
		appToken:appToken,
		week: week,
		level: level,
		relative: relative,
		tempContact: null
	};
	if(userType =="CAREGIVER"){
		body["caregiver"] = patient;
	} else {
		body["caregiver"] = caregiver;
	}
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
	text = /^[A-Za-z0-9+]*$/;
	phonenum = /^[0-9]+$/;
	comma = /,/g;
	passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/;
	search=/\s/g;


	if(isEmpty(removeSpace(id))){
		alert("아이디가 공백입니다.");
	}
	else if(search.test(id) >= 1) {
		alert("아이디는 공백 없이 입력해주세요.");
	}
	else if(id.length < 5 || id.length > 20) {
		alert("아이디는 5자리 ~ 20자리 이내로 입력해주세요.");
	}
	else if(!text.test(id)){
		alert("아이디는 영어와 숫자만 입력 가능합니다.");
	}
	else if (isEmpty(removeSpace(password))){
		alert("비밀번호가 공백입니다.");
	}
	else if(password.length < 8 || password.length > 16) {
		alert("비밀번호는 8자리 ~ 16자리 이내로 입력해주세요.");
	}
	else if(search.test(password) >= 1) {
		alert("비밀번호는 공백 없이 입력해주세요.");
	}
	else if(!passwordPattern.test(password)){
		alert("비밀번호는 영문, 숫자, 특수문자를 혼합하여 입력해주세요.");
	}
	else if (isEmpty(removeSpace(name))){
		alert("이름이 공백입니다.");
	}
	else if(comma.test(name)){
		alert("이름에 콤마는 사용 불가능 합니다.");
	}
	else if (isEmpty(removeSpace(contact))){
		alert("연락처(본인)가 공백입니다.");
	}
	else if(!phonenum.test(contact)){
		alert("연락처는 숫자만 입력 가능합니다.");
	}
	else {
		fetch(url, options)
			.then(response => response.json())
			.then(data => {
				console.log(data);
				if (data.code === 'OK') {
					window.location.href = "/login";
					alert("정상적으로 회원 가입 되었습니다.");
				} else if (data.code === 'ERROR_DUPLICATE_ID') {
					alert("이미 존재하는 아이디 입니다.");
				} else if(data.code === 'ERROR_DUPLICATE_CONTACT'){
					alert("이미 존재하는 번호 입니다.");
				}else if (data.code === 'ERROR_PATIENT_REFERENCE'){
					alert("환자를 보호자번호로 설정할 수 없습니다.");
				} else if (data.code === 'ERROR_SELF_REFERENCE'){
					alert("환자와 보호자는 번호가 같을 수 없습니다.");
				} else if (data.code === 'ERROR_NO_EXIST_CAREGIVER'){
					alert("존재하지 않는 보호자 번호 입니다. (공백 입력 또는 보호자 등록 해주세요)");
				} else if(data.code ==='ALREADY_CONTACT_EXIST') {
					alert("해당 환자의 보호자가 이미 존재합니다.");
				} else if(data.code==='EROR_RELATION') {
					alert("환자와의 관계를 정확히 입력해주세요.");
				} else if (data.code ==='NO_INPUT_PATIENT'){
					alert("환자의 번호를 입력해주세요.");
				} else if(data.code ==='ERROR_CAREGIVER_TEMP_CONTACT'){
					alert("등록된 환자의 번호가 다릅니다.");
				} else if(data.code ==='ERROR_PATIENT_TEMP_CONTACT') {
					alert("등록된 보호자의 번호가 다릅니다.");
				} else{
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
	const elems1 = document.getElementsByClassName("caregiver-only");

	if (user === 'CAREGIVER') {
		for (let i = 0; i < elems.length; i++) {
			elems[i].style.display = 'none'
		}
	} else {
		for (let i = 0; i < elems.length; i++) {
			elems[i].style.display = 'grid'
		}
	}

	if (user === 'PATIENT') {
		for (let i = 0; i < elems1.length; i++) {
			elems1[i].style.display = 'none'
		}
	} else {
		for (let i = 0; i < elems1.length; i++) {
			elems1[i].style.display = 'grid'
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

	if(value == "CAREGIVER")
	{
		return true;
	}
	else
	{
		return false;
	}
}
