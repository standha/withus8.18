function caregiverSubBody(tag) {
    let body = {
        family_bloodPressure: parseInt(document.querySelector("input[name=family_bloodPressure]").value),
        exercise: parseInt(document.querySelector("input[name=family_exercise]").value),
        highLevel: parseInt(document.querySelector("input[name=highLevel]").value),
        hof: parseInt(document.querySelector("input[name=hof]").value),
        lowLevel: parseInt(document.querySelector("input[name=lowLevel]").value),
        makeMyGoal: parseInt(document.querySelector("input[name=makeMyGoal]").value),
        medicine: parseInt(document.querySelector("input[name=family_medicine]").value),
        medicineTime: parseInt(document.querySelector("input[name=medicineTime]").value),
        middleLevel: parseInt(document.querySelector("input[name=middleLevel]").value),
        mindDiary: parseInt(document.querySelector("input[name=mindDiary]").value),
        mindHealth: parseInt(document.querySelector("input[name=family_mindHealth]").value),
        mindManagement: parseInt(document.querySelector("input[name=mindManagement]").value),
        mindScore: parseInt(document.querySelector("input[name=mindScore]").value),
        natriumMoisture: parseInt(document.querySelector("input[name=family_natriumMoisture]").value),
        notice: parseInt(document.querySelector("input[name=notice]").value),
        outPatientVisitTime: parseInt(document.querySelector("input[name=outPatientVisitTime]").value),
        question: parseInt(document.querySelector("input[name=question]").value),
        share: parseInt(document.querySelector("input[name=share]").value),
        symptom: parseInt(document.querySelector("input[name=family_symptom]").value),
        weight: parseInt(document.querySelector("input[name=family_weight]").value),
    };

// tag에 따라 해당 변수 증가
    const variablesToIncrement = [
        'family_bloodPressure',
        'family_exercise',
        'highLevel',
        'hof',
        'lowLevel',
        'makeMyGoal',
        'family_medicine',
        'medicineTime',
        'middleLevel',
        'mindDiary',
        'family_mindHealth',
        'mindManagement',
        'mindScore',
        'family_natriumMoisture',
        'notice',
        'outPatientVisitTime',
        'question',
        'share',
        'family_symptom',
        'family_weight'
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

