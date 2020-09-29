function onFormSubmission(form) {
	const enabled = form.querySelector("input[name=medicationAlarmOnoff]").checked;
	let breakfastHour = form.querySelector("input[name=morningHour]").value;
	let breakfastMinute = form.querySelector("input[name=morningMinute]").value;
	let lunchHour = form.querySelector("input[name=lunchHour]").value;
	let lunchMinute = form.querySelector("input[name=lunchMinute]").value;
	let dinnerHour = form.querySelector("input[name=dinnerHour]").value;
	let dinnerMinute = form.querySelector("input[name=dinnerMinute]").value;
	let morning;
	let lunch;
	let dinner;

	if(form.querySelector("input[name=morning]:checked").value == 1 ){
		breakfastHour = parseInt(breakfastHour) + 12;
	} else if(form.querySelector("input[name=morning]:checked").value == 0){
		breakfastHour = transHour(breakfastHour);
	}
	if(form.querySelector("input[name=lunch]:checked").value == 1 ){
		lunchHour = parseInt(lunchHour) + 12;
	}else if(form.querySelector("input[name=lunch]:checked").value == 0){
		lunchHour = transHour(lunchHour);
	}
	if(form.querySelector("input[name=dinner]:checked").value == 1 ){
		dinnerHour = parseInt(dinnerHour) + 12;
	}else if(form.querySelector("input[name=dinner]:checked").value == 0){
		dinnerHour = transHour(dinnerHour);
	}

	breakfastMinute = transMinute(breakfastMinute);
	lunchMinute = transMinute(lunchMinute);
	dinnerMinute = transMinute(dinnerMinute);

	if(breakfastHour == null || breakfastMinute ==null ){
		morning = null;
	}
	else{
		morning = breakfastHour+":"+breakfastMinute;
	}
	if(lunchHour == null || lunchMinute ==null ){
		lunch = null;
	}
	else{
		lunch = lunchHour+":"+lunchMinute;
	}
	if(dinnerHour == null || dinnerMinute ==null ){
		dinner = null;
	}
	else{
		dinner = dinnerHour+":"+dinnerMinute;
	}
	const body = {
		medicationAlarmOnoff: enabled,
		medicationTimeMorning: morning,
		medicationTimeLunch: lunch,
		medicationTimeDinner: dinner
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
		finished: finished
	};

	const url = document.querySelector("#pill-put-action").value;
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
		.then(object =>
		 {
			console.log(object);

			let data = object["data"];
			if (data)
			{
				let finishedResponse = data["finished"];

				if (finished === finishedResponse) 
				{
					if (finished) 
					{
						alert("복약하신 것으로 기록하였습니다.")
					} 
					else 
					{
						alert("아직 복약하지 않은 것으로 기록하였습니다.")
					}
				} 
				else 
				{
					alert("복약 처리에 실패했습니다.")
				}
			} 
			else 
			{
				alert("복약 처리에 실패했습니다.")
			}
		});
}

function showHistory() {
	const element = document.querySelector("#pill-history-get-action");
	location.href = element.value;
}

function check_radio(div) {
	switch (div.id) {
		case "yes":
			if (document.getElementById('yes').className === "rdo-box") {
				document.getElementById('yes').className = "rdo-box checked";
				document.getElementById('no').className = "rdo-box";
			}
			break;
		case "no":
			if (document.getElementById('no').className === "rdo-box") {
				document.getElementById('no').className = "rdo-box checked";
				document.getElementById('yes').className = "rdo-box";
			}
			break;
	}
}

function check_box()
{
	if (document.getElementById('alarm').className === "chk-box")
	{
		document.getElementById('alarm').className = "chk-box checked";
		document.getElementById('label').textContent = "켜짐";
	}
	else
	{
		document.getElementById('alarm').className = "chk-box";
		document.getElementById('label').textContent = "꺼짐";
	}
}

function check_morning(div) {
	switch (div.id) {
		case "morning_am":
			if (document.getElementById('morning_am').className === "rdo-box") {
				document.getElementById('morning_am').className = "rdo-box checked";
				document.getElementById('morning_pm').className = "rdo-box";
				document.getElementById("rdo8").setAttribute("checked", "");
				document.getElementById("rdo9").removeAttribute("checked");
			}
			break;
		case "morning_pm":
			if (document.getElementById('morning_pm').className === "rdo-box") {
				document.getElementById('morning_pm').className = "rdo-box checked";
				document.getElementById('morning_am').className = "rdo-box";
				document.getElementById("rdo9").setAttribute("checked", "");
				document.getElementById("rdo8").removeAttribute("checked");
			}
			break;
	}
}

function check_lunch(div) {
	switch (div.id) {
		case "lunch_am":
			if (document.getElementById('lunch_am').className === "rdo-box") {
				document.getElementById('lunch_am').className = "rdo-box checked";
				document.getElementById('lunch_pm').className = "rdo-box";
				document.getElementById("rdo10").setAttribute("checked", "");
				document.getElementById("rdo11").removeAttribute("checked");
			}
			break;
		case "lunch_pm":
			if (document.getElementById('lunch_pm').className === "rdo-box") {
				document.getElementById('lunch_pm').className = "rdo-box checked";
				document.getElementById('lunch_am').className = "rdo-box";
				document.getElementById("rdo11").setAttribute("checked", "");
				document.getElementById("rdo10").removeAttribute("checked");
			}
			break;
	}
}

function check_dinner(div) {
	switch (div.id) {
		case "dinner_am":
			if (document.getElementById('dinner_am').className === "rdo-box") {
				document.getElementById('dinner_am').className = "rdo-box checked";
				document.getElementById('dinner_pm').className = "rdo-box";
				document.getElementById("rdo12").setAttribute("checked", "");
				document.getElementById("rdo13").removeAttribute("checked");
			}
			break;
		case "dinner_pm":
			if (document.getElementById('dinner_pm').className === "rdo-box") {
				document.getElementById('dinner_pm').className = "rdo-box checked";
				document.getElementById('dinner_am').className = "rdo-box";
				document.getElementById("rdo13").setAttribute("checked", "");
				document.getElementById("rdo12").removeAttribute("checked");
			}
			break;
	}
}

function transHour(hour) {
	if(hour == 12){
		hour = parseInt(hour) -12;
		return "00";
	}
	else if(hour >= 10)
		return hour;
	else {
		if (hour == 0) {
			return null;
		} else if (hour.startsWith("0")) {
			return hour;
		} else {
			return "0" + hour;
		}
	}
}

function transMinute(min){
	if(min < 10){
		if(min.startsWith("00")){
			return min;
		}
		else if(min == 0){
			return null;
		}
		else if(min.startsWith("0")){
			return min;
		}
		else{
			return "0"+min;
		}
	}
	else
		return min;
}