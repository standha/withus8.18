function onFormSubmission(form) {

    const outofbreath = form.querySelector("input[name=outofbreath]:checked");
    const tired = form.querySelector("input[name=tired]:checked");
    const ankle = form.querySelector("input[name=ankle]:checked");
    const cough = form.querySelector("input[name=cough]:checked");

    const body = {
        outofbreath:outofbreath,
        tired:tired,
        ankle:ankle,
        cough:cough
    }
    console.log("dddd"+cough);
    const url = document.querySelector("#symptom-put-action").value;
    const options = {
        method: "PUT",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    };

    fetch(url, options)
        .then(response => response.json())
        .then(data => console.log(data));

    location.reload(true);
    top.document.location.reload();
    opener.location.reload();
    top.document.location.reload();
    return false;
}

