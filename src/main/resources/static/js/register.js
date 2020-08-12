function onFormSubmission(form) {
	const id = form.querySelector("input[name=id]").value;
	const password = form.querySelector("input[pass=password]").checked;
	const name = form.querySelector("input[name=name]").value;
	const contact = form.querySelector("input[name=contact]").value;
	const birthdate = form.querySelector("input[name=birthdate]").value;
	const sex = form.querySelector("input[name=sex]").value;
	const caregiver = form.querySelector("input[name=caregiver]").value;


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

function doDisplay(user) {
	console.log(user)
	var elems = document.getElementsByClassName("patient-only");

	if(user === 'caregiver') {
		for(var i=0; i<elems.length; i++) {
			elems[i].style.display = 'none'
		}
	} else {
		for(var i=0; i<elems.length; i++) {
			elems[i].style.display = 'grid'
		}
	}
}
