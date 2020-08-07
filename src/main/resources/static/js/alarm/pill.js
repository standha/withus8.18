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

	const response = fetch(url, options);
	if (response && response.ok) {
		console.log(response);
	}

	return false;
}
