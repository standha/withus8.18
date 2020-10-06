function setMinus(testNum) {
	testNum = parseInt(testNum);
	if(testNum <= 0){
		//alert('아직 오늘 물을 한 잔도 마시지 않으셨어요.');
	}else{
		testNum = testNum - 1;
		setTimeout(function (){
			window.location.reload();
		}, 300);

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
		.then(data => console.log(data));

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
		setTimeout(function (){
			window.location.reload();
		}, 300);

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
		.then(data => console.log(data));

	return false;
}

function showHistory() {
	const element = document.querySelector("#moisture-history-get-action");
	location.href = element.value;
}
