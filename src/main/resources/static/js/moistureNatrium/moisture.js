function setMinus(testNum) {
	testNum = parseInt(testNum);
	if(testNum <= 0){
		//alert('아직 오늘 물을 한 잔도 마시지 않으셨어요.');
	}else{
		testNum = testNum - 1;
		setTimeout(function (){
			window.location.reload();
		}, 100);

	}
	const mbody = {
		intake: testNum
	};
	console.log('After num: ' + testNum);
	const url = document.querySelector("#moisture-put-action").value;
	const options = {
		method: "PUT",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		},
		body: JSON.stringify(mbody)
	};


	fetch(url, options)
		.then(response => response.json())
		.then(data => {
			console.log(data);
			if (data.code === 'OK') {
				alert("수정이 완료되었습니다.");
			}  else {
				alert("입력 실패하였습니다. 관리자에게 문의해주세요.");
			}
		});

	return false;
}

function popupClose(){
	$("#popup").hide();
	$("#dim").hide();
}

function popupOpen(){
	$("#popup").show();
	$("#dim").show();
}

function setAdd(testNum) {
	testNum = parseInt(testNum);
	console.log(testNum);
	if(testNum >= 10){
		popupOpen();
	}
	else{
		testNum = testNum + 1;
		alert("입력이 완료되었습니다.");
		setTimeout(function (){
			window.location.reload();
		}, 100);

	}
	const body = {
		intake: testNum
	};

	console.log('After num: ' + testNum);
	const url = document.querySelector("#moisture-put-action").value;
	const options = {
		method: "PUT",
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
			}  else {
				alert("입력 실패하였습니다. 관리자에게 문의해주세요.");
			}
		});

/*
	fetch(url, options)
		.then(response => response.json())
		.then(data =>
			console.log(data);
	if
	);*/

	return false;
}

function showHistory() {
	const element = document.querySelector("#moisture-history-get-action");
	location.href = element.value;
}
