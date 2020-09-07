function setMinus(testNum) {
	testNum = parseInt(testNum);
	if(testNum <= 0){alert('물을 아직 한잔도 드시지 않으셨어요 ');
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

	location.reload(true);
	top.document.location.reload();
	opener.location.reload();
	top.document.location.reload();
	return false;
}

function setAdd(testNum) {
	testNum = parseInt(testNum);
	if(testNum >= 10){alert('물을 너무 많이 드신거 같지 않나요?');
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

	location.reload(true);
	top.document.location.reload();
	opener.location.reload();
	top.document.location.reload();
	return false;
}

function showHistory() {
	const element = document.querySelector("#moisture-history-get-action");
	location.href = element.value;
}
