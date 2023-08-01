var inputCheck = true;

function onFormSubmission(form) {
	let weight = null;
	if(form.querySelector("input[name=weight]").value == 0 )
	{
		inputCheck =false;
	}
	else{
		weight =form.querySelector("input[name=weight]").value;
		inputCheck = true;
		submit_Click();
	}
	if(inputCheck === false){
		alert("모든 데이터를 입력해주세요.");
		window.location.reload();
	}
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
	if(inputCheck === true){
		fetch(url, options)
			.then(response => response.json())
			.then(data => {
				console.log(data);
				if (data.code === 'OK') {
					console.log("입력 성공하였습니다.")
				}  else {
					console.log("기록 실패하였습니다. 관리자에게 문의해주세요.");
					alert("기록 실패하였습니다. 관리자에게 문의해주세요.");
				}
			});
	}


	return false;
}


function ChangeWeightAlarm(value){
	const today = document.getElementsByTagName("form")[0].querySelector("input[name=weight]").value;
	const changeAmount = parseInt(today) - parseInt(value);
	if(changeAmount >= 2 || changeAmount <= -2){
		$("#layerSelectType").show();
		$("#dim").show();
		$("#buttonCaregiverType").hide();
		$("#buttonRecordType").show();
		$('#popUp2').find('span').remove();
		Item2 = "<span>체중이 전날에 비해 "+Math.abs(changeAmount)+"kg 변화되었어요!</span>";
		$('#popUp2').append(Item2);

		$("#buttonCheck").click(function () {
			$("#layerSelectType").hide();
			$("#dim").hide();
			$("#buttonRecordType").hide();
			window.location.reload();

			return false;
		});
	}
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

function weight_caregiver() {
	var Item;
	if(inputCheck){
		if(document.getElementsByTagName("form")[0].querySelector("input[name=weight]").value == 0){
		} else {
			const body = {
				weight: document.getElementsByTagName("form")[0].querySelector("input[name=weight]").value,
			};
			const url = document.getElementsByTagName("form")[0].action;
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
				.then(data => {
					console.log(data);
					if (data.code === 'OK') {
						console.log("입력 성공하였습니다.")
					}  else {
						console.log("기록 실패하였습니다. 관리자에게 문의해주세요.");
						alert("기록 실패하였습니다. 관리자에게 문의해주세요.");
					}
				});
		}

		$("#layerSelectType").show();
		$("#dim").show();
		$("#buttonCaregiverType").show();
		$("#buttonRecordType").hide();
		Item = "<span> 보호자에게 체중을 알릴까요?</span>";
		$('#popUp2').append(Item);


		$("#buttonOk").click(function () {
			$("#layerSelectType").hide();
			$("#dim").hide();
			$("#buttonCaregiverType").hide();

			window.location.reload();
			return false;
		});

		$("#buttonNo").click(function () {
			$("#layerSelectType").hide();
			$("#dim").hide();
			$("#buttonCaregiverType").hide();


			window.location.reload();
			return false;
		});

	}
}
function submit_Click() {

	var Item2;
	if(inputCheck){
		$("#layerSelectType").show();
		$("#dim").show();
		$("#buttonCaregiverType").hide();
		$("#buttonRecordType").show();
		Item2 = "<span>기록이 완료되었습니다.</span>";
		$('#popUp2').append(Item2);
		$("#buttonCheck").click(function () {
			$("#layerSelectType").hide();
			$("#dim").hide();
			$("#buttonRecordType").hide();

			const oneDayBefore = document.getElementById('oneDayBefore').value
			if(oneDayBefore === ''){
				window.location.reload();
			} else {
				ChangeWeightAlarm(oneDayBefore)
			}
			return false;
		});

	}


}
document.addEventListener("DOMContentLoaded", function () {
	// "저장" 버튼에 클릭 이벤트 핸들러를 추가합니다. 버튼은 id="saveButton"으로 선택됩니다.
	const saveButton = document.getElementById("saveButton");
	if (saveButton) {
		saveButton.addEventListener("click", function (event) {
			event.preventDefault(); // 기본 폼 제출 동작을 방지합니다.
		});
	}
});

