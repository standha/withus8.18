function showTime() {
	const form = document.querySelector("#appointment-form");

	const dateInput = document.querySelector("#date input[name=outPatientVisitDate]");
	const dateValue = dateInput.value;

	const url = (form.action + "/" + dateValue);
	const options = {
		method: "GET",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		}
	};

	fetch(url, options)
		.then(response => response.json())
		.then(object => {
			console.log(object);

			let ok = false;
			if (object) {
				if (object["code"] === "OK") {
					const data = object["data"];
					form.querySelector("input[name=id]").value = data["id"];
					form.querySelector("input[name=enabled]").checked = data["enabled"];
					form.querySelector("input[name=time]").value = data["timeString"];

					ok = true;
				} else if (object["code"].indexOf("OK") === 0) {
					form.querySelector("input[name=id]").value = "";
					form.querySelector("input[name=enabled]").checked = false;
					form.querySelector("input[name=time]").value = null;

					ok = true;
				} else {
					alert(object["code"]);
				}
			}

			if (ok) {
				form.querySelector("#time").classList.remove("hidden");
			}
		});
}

function onFormSubmission(form) {
	const enabled = form.querySelector("input[name=visitAlarm]").checked;
	const date = form.querySelector("input[name=outPatientVisitDate]").value
	const time = form.querySelector("input[name=outPatientVisitTime]").value;

	const body = {
		visitAlarm: enabled,
		outPatientVisitDate: date,
		outPatientVisitTime: time
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
