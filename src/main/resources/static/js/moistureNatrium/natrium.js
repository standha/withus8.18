function onFormSubmission(form) {
    const morningElement = form.querySelector("input[name=morning]:checked");
    const morning = morningElement? morningElement.value: null;

    const launchElement = form.querySelector("input[name=launch]:checked");
    const launch = launchElement? launchElement.value: null;

    const dinnerElement = form.querySelector("input[name=dinner]:checked");
    const dinner = dinnerElement? dinnerElement.value: null;

    const body = {
        morning : morning,
        launch : launch,
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

