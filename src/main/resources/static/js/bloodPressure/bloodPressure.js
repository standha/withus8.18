function onFormSubmission(form) {
    const contraction = form.querySelector("input[name=contraction]").value;
    const relaxation = form.querySelector("input[name=relaxation]").value;
    const pressure = form.querySelector("input[name=pressure]").value;
    const body = {
        contraction: contraction,
        relaxation: relaxation,
        pressure: pressure
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
    const element = document.querySelector("#bloodPressure-history-get-action");
    location.href = element.value;
}
