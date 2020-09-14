function onFormSubmission(form) {
    const userType = form.querySelector("input[name=user]:checked").value;
    const id = form.querySelector("input[name=id]").value;
    const password = form.querySelector("input[name=password]").value;
    const name = form.querySelector("input[name=name]").value;
    const contact = form.querySelector("input[name=contact]").value;
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
    const appToken = form.querySelector("input[name=appToken]").value;
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
        appToken:appToken
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
