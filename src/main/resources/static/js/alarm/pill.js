function onFormSubmission(form) {
	const id = form.querySelector("input[name=id]").value;
	const enabled = form.querySelector("input[name=enabled]").checked;
	const breakfast = form.querySelector("input[name=breakfast]").value;
	const lunch = form.querySelector("input[name=lunch]").value;
	const dinner = form.querySelector("input[name=dinner]").value;

	const body = {
		id: id,
		enabled: enabled,
		breakfast: breakfast,
		lunch: lunch,
		dinner: dinner
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
		.then(data => console.log(data));

	return false;
}

function setFinished(finished) {
	const body = {
		pillId: document.querySelector("#pill-form input[name=id]").value,
		finished: finished
	};

	const url = document.querySelector("#pill-put-action").value;
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
		.then(object => {
			console.log(object);

			let data = object["data"];
			if (data) {
				let finishedResponse = data["finished"];

				if (finished === finishedResponse) {
					if (finished) {
						alert("복약하신 것으로 기록하였습니다.")
					} else {
						alert("아직 복약하지 않은 것으로 기록하였습니다.")
					}
				} else {
					alert("복약 처리에 실패했습니다.")
				}
			}
		});
}

function showHistory() {

}
