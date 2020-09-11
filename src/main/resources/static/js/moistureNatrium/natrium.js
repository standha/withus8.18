function onFormSubmission(form) {
    const morningElement = form.querySelector("input[name=morning]:checked");
    const morning = morningElement? morningElement.value: 0;

    const lunchElement = form.querySelector("input[name=lunch]:checked");
    const lunch = lunchElement? lunchElement.value: 0;

    const dinnerElement = form.querySelector("input[name=dinner]:checked");
    const dinner = dinnerElement? dinnerElement.value: 0;

    const body = {
        morning : morning,
        lunch : lunch,
        dinner : dinner
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
    const element = document.querySelector("#natrium-history-get-action");
    location.href = element.value;
}