function sendHelpRequest() {
	const url = document.querySelector("#help-request-url").value;
	const options = {
		method: "POST",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		}
	};

	fetch(url, options)
	.then(response => response.json())
	.then(object => {
		const code = object["code"];
		if (code === "OK") {
			const data = object["data"];
			console.log(data);

			const userName = data["user"]["name"];
			if (userName) {
				alert(`${userName}님, 위더스 도우미가 오전 9시~오후 6시 사이에 연락드릴게요.`);
			} else {
				alert("네. 위더스 도우미가 오전 9시~오후 6시 사이에 연락드릴게요.");
			}
		} else {
			alert("위더스 도우미 호출에 실패하였습니다.");
		}
	});
}
