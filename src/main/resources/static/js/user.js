function onFormSubmission(form) {
	const id = form.querySelector("input[name=id]").value;
	const password = form.querySelector("input[name=password]").value;
	const name = form.querySelector("input[name=name]").value;
	const contact = form.querySelector("input[name=contact]").value;
	const birthdate = form.querySelector("input[name=birthdate]").value;
	const sex = form.querySelector("input[name=sex]").value;
	/*const caregiver = form.querySelector("input[name=caregiver]").value;*/


	const body = {
		id: id,
		password: password,
		name: name,
		contact: contact,
		birthdate: birthdate,
		sex: sex,
		/*caregiver: caregiver*/
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
