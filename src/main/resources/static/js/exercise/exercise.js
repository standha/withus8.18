function onFormSubmission(form) {
    const hour = form.querySelector("input[name=hour]").value
    const minute = form.querySelector("input[name=minute]").value;

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
