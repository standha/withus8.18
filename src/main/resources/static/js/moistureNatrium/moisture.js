function setMinus(testNum) {
	testNum = parseInt(testNum);
	if(testNum <= 0){alert('아직 오늘 물을 한 잔도 마시지 않으셨어요.');
	}else{ testNum = testNum - 1;}
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

	// location.reload(true);
	// top.document.location.reload();
	// opener.location.reload();
	// top.document.location.reload();
	window.location.reload();
	return false;
}

function setAdd(testNum) {
	testNum = parseInt(testNum);
	if(testNum >= 10){alert('오늘 마신 물의 양도 충분해요!\n너무 많은 수분을 섭취하지 마세요.');
	}else{ testNum = testNum + 1;}
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

	// location.reload(true);
	// top.document.location.reload();
	// opener.location.reload();
	// top.document.location.reload();
	window.location.reload();
	return false;
}

function showHistory() {
	const element = document.querySelector("#moisture-history-get-action");
	location.href = element.value;
}
