function onFormSubmission(form) {
	const id = form.querySelector("input[name=id]").value;
	const weight = form.querySelector("input[name=weight]").value;
	const date = form.querySelector("button[name=date]").value;

	const body = {
		id: id,
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


	location.reload(true);
	top.document.location.reload();
	opener.location.reload();
	top.document.location.reload();

	return true;
}

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
	
}

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