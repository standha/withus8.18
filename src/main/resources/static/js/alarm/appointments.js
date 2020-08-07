function showTime() {
	const dateInput = document.querySelector("#date input[name=date]");
	const dateValue = dateInput.value;

	const url = ("appointment/" + dateValue);
	const options = {
		method: "GET",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		}
	};

	const response = fetch(url, options);
	if (response && response.ok) {
		const form = document.querySelector("#appointment-form");
		response.json().then(object => {
			if (object) {
				form.querySelector("input[name=id]").value = object["id"];
				form.querySelector("input[name=enabled]").checked = object["enabled"];
				form.querySelector("input[name=time]").value = object["time"];
			}

			form.querySelector("#time.hidden").classList.remove("hidden");
		});
	}
}

function onFormSubmission(form) {
	const id = form.querySelector("input[name=id]").value;
	const enabled = form.querySelector("input[name=enabled]").checked;
	const date = form.querySelector("input[name=date]").value
	const time = form.querySelector("input[name=time]").value;

	const body = {
		id: id,
		enabled: enabled,
		date: date,
		time: time
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

	const response = fetch(url, options);
	if (response && response.ok) {
		console.log(response);
	}

	return false;
}
