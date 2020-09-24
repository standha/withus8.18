function onFormSubmission(form) {
    if(form.querySelector('input[name=breath]:checked')==null ||
        form.querySelector('input[name=tired]:checked')==null ||
        form.querySelector('input[name=ankle]:checked')==null ||
        form.querySelector('input[name=cough]:checked')==null )
    {
        alert("항목 입력을 빠트리셨어요!\n입력해주세요!");
    }
    else{
        breath = form.querySelector('input[name=breath]:checked').value;
        tired = form.querySelector('input[name=tired]:checked').value;
        ankle = form.querySelector('input[name=ankle]:checked').value;
        cough = form.querySelector('input[name=cough]:checked').value;
    }
    const sum = parseInt(breath)+parseInt(tired)+parseInt(ankle)+parseInt(cough);
    const body = {
        outofbreath:breath,
        tired:tired,
        ankle:ankle,
        cough:cough,
        todaysymptom:sum
    }

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

function showHistory() {
    const element = document.querySelector("#symptom-history-get-action");
    location.href = element.value;
}
