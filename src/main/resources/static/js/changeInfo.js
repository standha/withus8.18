function onFormSubmission(form) {
    const userType = form.querySelector("input[name=user]").value;
    const id = form.querySelector("input[name=id]").value;
    const password = form.querySelector("input[name=password]").value;
    const name = form.querySelector("input[name=name]").value;
    const contact = form.querySelector("input[name=contact]").value;
    const level = form.querySelector("input[name=level]").value;
    const week = form.querySelector("input[name=week]").value;
    const token = form.querySelector("input[name=appToken]").value;
    const registrationDateTime = form.querySelector("input[name = registrationDateTime]").value;
    const appToken = isEmpty(token) ? null : token;
    if(form.querySelector("input[name=birthdate]") == null){
        birthdateValue =  null;
        birthdate = null;
    }else{

        birthdateValue  = form.querySelector("input[name=birthdate]") .value;
        birthdate =    isEmpty(birthdateValue) ? null : birthdateValue;
    }
    if(form.querySelector("input[name=gender]:checked") == null){
        sexElement = null;
        sex = null;
    }else{
        sexElement = form.querySelector("input[name=gender]:checked");
        sex = sexElement? sexElement.value: null;
    }
    if( form.querySelector("input[name=caregiver]") == null){
        caregiverValue = null;
    }else{
        caregiverValue = form.querySelector("input[name=caregiver]").value;
    }
    const user = {
        userId: null,
        password: null,
        name: null,
        contact: caregiverValue
    }
    if(caregiverValue == null){
        caregiver = null;
    }else{
        caregiver = isEmpty(caregiverValue) ? null : user;
    }

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
        level:level,
        week:week,
        registrationDateTime:registrationDateTime
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
    text = /^[A-Za-z0-9+]*$/;
    phonenum = /^[0-9]+$/;
    comma = /,/g;
    if(isEmpty(removeSpace(id))){
        alert("아이디가 공백입니다.");
    }
    else if(!text.test(id)){
        alert("아이디는 영어와 숫자만 입력 가능합니다.");
    }
    else if (isEmpty(removeSpace(password))){
        alert("비밀번호가 공백입니다.");
    }
    else if(!text.test(password)){
        alert("비밀번호는 영어와 숫자만 입력 가능합니다.");
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
                    window.location.href = "/center";
                    alert("정보 수정이 완료되었습니다.");
                }else {
                    alert("정보 수정 실패");
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