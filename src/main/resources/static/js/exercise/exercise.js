function onFormSubmission(form) {
    const hourValue = form.querySelector("input[name=hour]").value
    const minuteValue = form.querySelector("input[name=minute]").value;
    if(isEmpty(hourValue)==true && isEmpty(minuteValue)==true ) {

    } else{
        hour = isEmpty(hourValue) ? 0 : hourValue;
        minute = isEmpty(minuteValue)? 0 : minuteValue;
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

function isEmpty(value){
    if (value.length === 0) {
        return true;
    } else {
        return false;
    }
}