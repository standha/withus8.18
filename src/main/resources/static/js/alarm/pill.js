function onFormSubmission(form) {
	let id = form.querySelector("input[name=id]").value;
	let enabled = form.querySelector("input[name=enabled]").value;
	let breakfast = form.querySelector("input[name=breakfast]").value;
	let lunch = form.querySelector("input[name=lunch]").value;
	let dinner = form.querySelector("input[name=dinner]").value;

	let url = form.action;
	let options = {
		method: "POST",
		/*mode: "cors",*/
		headers: {
			/*"accept": "*!/!*"*/
		},
	};
	let body = {
		id: id,
		enabled: enabled,
		breakfast: breakfast,
		lunch: lunch,
		dinner: dinner
	};

	let response = fetch(url, options);
	if (response && response.ok) {
		console.log(response);
	}

	return false;
}
