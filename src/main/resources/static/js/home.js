function patientMainBody(tag) {
    let body = {
        alarm: parseInt(document.querySelector("input[name=alarm]").value),
        symptom: parseInt(document.querySelector("input[name=symptom]").value),
        bloodPressure: parseInt(document.querySelector("input[name=bloodPressure]").value),
        diseaseInfo: parseInt(document.querySelector("input[name=diseaseInfo]").value),
        exercise: parseInt(document.querySelector("input[name=exercise]").value),
        goal: parseInt(document.querySelector("input[name=goal]").value),
        helper: parseInt(document.querySelector("input[name=helper]").value),
        level: parseInt(document.querySelector("input[name=level]").value),
        natriumMoisture: parseInt(document.querySelector("input[name=natriumMoisture]").value),
        weight: parseInt(document.querySelector("input[name=weight]").value),
        withusRang: parseInt(document.querySelector("input[name=withusRang]").value),
        board: parseInt(document.querySelector("input[name=board]").value),
        medicine: parseInt(document.querySelector("input[name=medicine]").value),
        infoEdit: parseInt(document.querySelector("input[name=infoEdit]").value),
        mindHealth: parseInt(document.querySelector("input[name=mindHealth]").value)
    };

    // tag에 따라 해당 변수 증가
    const variablesToIncrement = [
        'symptom',
        'bloodPressure',
        'diseaseInfo',
        'exercise',
        'goal',
        'helper',
        'level',
        'natriumMoisture',
        'weight',
        'withusRang',
        'board',
        'medicine',
        'infoEdit',
        'mindHealth',
        'alarm'
    ];

    if (variablesToIncrement.includes(tag)) {
        body[tag] += 1;
    }

    return body;
}


function caregiverMainBody(tag) {
    let body = {
        alarm: parseInt(document.querySelector("input[name=alarm]").value),
        dietManagement: parseInt(document.querySelector("input[name=dietManagement]").value),
        bloodPressure: parseInt(document.querySelector("input[name=bloodPressure]").value),
        diseaseInfo: parseInt(document.querySelector("input[name=diseaseInfo]").value),
        exercise: parseInt(document.querySelector("input[name=exercise]").value),
        goal: parseInt(document.querySelector("input[name=goal]").value),
        helper: parseInt(document.querySelector("input[name=helper]").value),
        level: parseInt(document.querySelector("input[name=level]").value),
        familyObservation: parseInt(document.querySelector("input[name=familyObservation]").value),
        weight: parseInt(document.querySelector("input[name=weight]").value),
        withusRang: parseInt(document.querySelector("input[name=withusRang]").value),
        board: parseInt(document.querySelector("input[name=board]").value),
        medicine: parseInt(document.querySelector("input[name=medicine]").value),
        infoEdit: parseInt(document.querySelector("input[name=infoEdit]").value),
        mindHealth: parseInt(document.querySelector("input[name=mindHealth]").value)
    };

    // tag에 따라 해당 변수 증가
    const variablesToIncrement = [
        'dietManagement',
        'bloodPressure',
        'diseaseInfo',
        'exercise',
        'goal',
        'helper',
        'level',
        'familyObservation',
        'weight',
        'withusRang',
        'board',
        'medicine',
        'infoEdit',
        'mindHealth',
        'alarm'
    ];

    if (variablesToIncrement.includes(tag)) {
        body[tag] += 1;
    }

    return body;
}


function postButtonCount(menu) {
    let body = {};
    const url = document.querySelector("#button-count").value;
    if(url ==="/patient-main-button-count"){
        body = patientMainBody(menu);
    } else {
        body = caregiverMainBody(menu);
    }

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

    return false;
}

