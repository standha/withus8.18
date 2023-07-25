function caregiverSubBody(tag) {
    let body = {
        bloodPressure: parseInt(document.querySelector("input[name=bloodPressure]").value),
        exercise: parseInt(document.querySelector("input[name=exercise]").value),
        highLevel: parseInt(document.querySelector("input[name=highLevel]").value),
        hof: parseInt(document.querySelector("input[name=hof]").value),
        lowLevel: parseInt(document.querySelector("input[name=lowLevel]").value),
        makeMyGoal: parseInt(document.querySelector("input[name=makeMyGoal]").value),
        medicine: parseInt(document.querySelector("input[name=medicine]").value),
        medicineTime: parseInt(document.querySelector("input[name=medicineTime]").value),
        middleLevel: parseInt(document.querySelector("input[name=middleLevel]").value),
        mindDiary: parseInt(document.querySelector("input[name=mindDiary]").value),
        mindHealth: parseInt(document.querySelector("input[name=mindHealth]").value),
        mindManagement: parseInt(document.querySelector("input[name=mindManagement]").value),
        mindScore: parseInt(document.querySelector("input[name=mindScore]").value),
        natriumMoisture: parseInt(document.querySelector("input[name=natriumMoisture]").value),
        notice: parseInt(document.querySelector("input[name=notice]").value),
        outPatientVisitTime: parseInt(document.querySelector("input[name=outPatientVisitTime]").value),
        question: parseInt(document.querySelector("input[name=question]").value),
        share: parseInt(document.querySelector("input[name=share]").value),
        symptom: parseInt(document.querySelector("input[name=symptom]").value),
        weight: parseInt(document.querySelector("input[name=weight]").value),
    };

// tag에 따라 해당 변수 증가
    const variablesToIncrement = [
        'bloodPressure',
        'exercise',
        'highLevel',
        'hof',
        'lowLevel',
        'makeMyGoal',
        'medicine',
        'medicineTime',
        'middleLevel',
        'mindDiary',
        'mindHealth',
        'mindManagement',
        'mindScore',
        'natriumMoisture',
        'notice',
        'outPatientVisitTime',
        'question',
        'share',
        'symptom',
        'weight'
    ];

    if (variablesToIncrement.includes(tag)) {
        body[tag] += 1;
    }

    return body;
}



function patientSubBody(tag) {
    let body = {
        highLevel: parseInt(document.querySelector("input[name=highLevel]").value),
        hof: parseInt(document.querySelector("input[name=hof]").value),
        lowLevel: parseInt(document.querySelector("input[name=lowLevel]").value),
        makeMyGoal: parseInt(document.querySelector("input[name=makeMyGoal]").value),
        medicineTime: parseInt(document.querySelector("input[name=medicineTime]").value),
        middleLevel: parseInt(document.querySelector("input[name=middleLevel]").value),
        mindDiary: parseInt(document.querySelector("input[name=mindDiary]").value),
        mindManagement: parseInt(document.querySelector("input[name=mindManagement]").value),
        mindScore: parseInt(document.querySelector("input[name=mindScore]").value),
        natriumMoisture: parseInt(document.querySelector("input[name=natriumMoisture]").value),
        notice: parseInt(document.querySelector("input[name=notice]").value),
        outPatientVisitTime: parseInt(document.querySelector("input[name=outPatientVisitTime]").value),
        question: parseInt(document.querySelector("input[name=question]").value),
        share: parseInt(document.querySelector("input[name=share]").value),
        waterIntake: parseInt(document.querySelector("input[name=waterIntake]").value)
    };

    // tag에 따라 해당 변수 증가
    const variablesToIncrement = [
        'highLevel',
        'hof',
        'lowLevel',
        'makeMyGoal',
        'medicineTime',
        'middleLevel',
        'mindDiary',
        'mindManagement',
        'mindScore',
        'natriumMoisture',
        'notice',
        'outPatientVisitTime',
        'question',
        'share',
        'waterIntake'
    ];

    if (variablesToIncrement.includes(tag)) {
        body[tag] += 1;
    }

    return body;
}


function postButtonCount(menu) {
    let body = {};
    const url = document.querySelector("#button-count").value;
    if(url ==="/patient-sub-button-count"){
        body = patientSubBody(menu);
    } else {
        body = caregiverSubBody(menu);
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

