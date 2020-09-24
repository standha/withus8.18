function onFormSubmission(form) {
    // const hour = form.querySelector("input[name=hour]").value
    // const minute = form.querySelector("input[name=minute]").value;

    if (form.querySelector("input[name=hour]").value == null && form.querySelector("input[name=minute]").value == null)
    {
        alert("시간을 입력해 주세요!");
    }
    else if(form.querySelector("input[name=hour]").value == null)
    {
        hour = 0;
        minute = form.querySelector("input[name=minute]").value;
    }
    else if(form.querySelector("input[name=minute]").value == null)
    {
        hour = form.querySelector("input[name=hour]").value;
        minute = 0;
    }
    else
    {
        hour = form.querySelector("input[name=hour]").value;
        minute = form.querySelector("input[name=minute]").value;
    }

    const body = {
        hour: hour,
        minute: minute
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

function showHistory() {
    const element = document.querySelector("#exercise-history-get-action");
    location.href = element.value;
}
