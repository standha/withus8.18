function onFormSubmission(form) {
	if(form.querySelector("input[name=weight]").value == 0){}
	else
		weight = form.querySelector("input[name=weight]").value;

	const body = {
		weight: weight
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

	fetch(url, options)
		.then(response => response.json())
		.then(data =>console.log(data));

	return false;
}

/* 이 방법은 틀렸다고 말하기는 애매하지만, 좋은 방법은 아님, 일단 html 에서 form 형식으로 묶어서 form input 텍스트에 있는 값을 강제로 버튼 setfinished로 들고와서
이 값을 보내준다. 근데 이 방법이 put 방식? 은 좋은 방법이 아닐듯 함, https://html.spec.whatwg.org/multipage/forms.html#the-form-element  *참조
function setFinished(finished){

	const body = {
		//weightId: document.querySelector("#weight-form input[name=id]").value,
		weight: document.querySelector("#weight-form input[name=weight]").value
	};

	const url = document.querySelector("#weight-put-action").value;
	const options = {
		method: "PUT",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		},
		body: JSON.stringify(body)
	};

	fetch(url, options);

	location.reload(true);
	top.document.location.reload();
	opener.location.reload();
	top.document.location.reload();
	
}*/

function isNumberKey(evt){
		//체중 입력받는 부분

		var charCode=(evt.which)?evt.which:event.keyCode;
		if(charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
			return false;
		
		var _value = event.srcElement.value;
		
		//소수점 두번 이상 못나오게
		var _pattern0 = /^\d*[.]\d*$/;
		if (_pattern0.test(_value)) {
            if (charCode == 46) {
                return false;
            }
        }
		
		//소수점 첫재짜리까지만 입력
		var _pattern1 = /^\d*[.]\d{1}$/;
		if(_pattern1.test(_value))
			return false;
		
		return true;
}

function setOff(){
	const date = document.getElementById('date');
	date.disabled='disabled';

	const weight = document.getElementsByClassName('weight');
	weight.disabled='disabled';

}


function showWeightHistory() {
	const element = document.querySelector("#weight-history-get-action");
	location.href = element.value;
}