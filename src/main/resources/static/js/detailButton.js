function caregiverDetailBody(tag) {
    let body = {
        bloodPressureAlarm: parseInt(document.querySelector("input[name=bloodPressureAlarm]").value),
        bodyActivity: parseInt(document.querySelector("input[name=bodyActivity]").value),
        consulting: parseInt(document.querySelector("input[name=consulting]").value),
        deepBreath: parseInt(document.querySelector("input[name=deepBreath]").value),
        exerciseAlarm: parseInt(document.querySelector("input[name=exerciseAlarm]").value),
        medicineAlarm: parseInt(document.querySelector("input[name=medicineAlarm]").value),
        mindDiaryAlarm: parseInt(document.querySelector("input[name=mindDiaryAlarm]").value),
        meditation: parseInt(document.querySelector("input[name=meditation]").value),
        mindScoreAlarm: parseInt(document.querySelector("input[name=mindScoreAlarm]").value),
        natriumMoistureAlarm: parseInt(document.querySelector("input[name=natriumMoistureAlarm]").value),
        recommendDiet: parseInt(document.querySelector("input[name=recommendDiet]").value),
        symptomAlarm: parseInt(document.querySelector("input[name=symptomAlarm]").value),
        waterIntakeAlarm: parseInt(document.querySelector("input[name=waterIntakeAlarm]").value),
        weightAlarm: parseInt(document.querySelector("input[name=weightAlarm]").value),
    };

    // tag에 따라 해당 변수 증가
    const variablesToIncrement = [
        'bloodPressureAlarm',
        'bodyActivity',
        'consulting',
        'deepBreath',
        'exerciseAlarm',
        'medicineAlarm',
        'mindDiaryAlarm',
        'meditation',
        'mindScoreAlarm',
        'natriumMoistureAlarm',
        'recommendDiet',
        'symptomAlarm',
        'waterIntakeAlarm',
        'weightAlarm'
    ];

    if (variablesToIncrement.includes(tag)) {
        body[tag] += 1;
    }

    return body;
}



function patientDetailBody(tag) {
    let body = {
        bloodPressureAlarm: parseInt(document.querySelector("input[name=bloodPressureAlarm]").value),
        bodyActivity: parseInt(document.querySelector("input[name=bodyActivity]").value),
        consulting: parseInt(document.querySelector("input[name=consulting]").value),
        deepBreath: parseInt(document.querySelector("input[name=deepBreath]").value),
        exerciseAlarm: parseInt(document.querySelector("input[name=exerciseAlarm]").value),
        medicineAlarm: parseInt(document.querySelector("input[name=medicineAlarm]").value),
        meditation: parseInt(document.querySelector("input[name=meditation]").value),
        mindScoreAlarm: parseInt(document.querySelector("input[name=mindScoreAlarm]").value),
        natriumMoistureAlarm: parseInt(document.querySelector("input[name=natriumMoistureAlarm]").value),
        recommendDiet: parseInt(document.querySelector("input[name=recommendDiet]").value),
        symptomAlarm: parseInt(document.querySelector("input[name=symptomAlarm]").value),
        waterIntakeAlarm: parseInt(document.querySelector("input[name=waterIntakeAlarm]").value),
        weightAlarm: parseInt(document.querySelector("input[name=weightAlarm]").value),
    };

    // tag에 따라 해당 변수 증가
    const variablesToIncrement = [
        'bloodPressureAlarm',
        'bodyActivity',
        'consulting',
        'deepBreath',
        'exerciseAlarm',
        'medicineAlarm',
        'meditation',
        'mindScoreAlarm',
        'natriumMoistureAlarm',
        'recommendDiet',
        'symptomAlarm',
        'waterIntakeAlarm',
        'weightAlarm'
    ];

    if (variablesToIncrement.includes(tag)) {
        body[tag] += 1;
    }

    return body;
}


function postButtonCount(menu) {
    let body = {};
    const url = document.querySelector("#button-count").value;
    if(url ==="/patient-detail-button-count"){
        body = patientDetailBody(menu);
    } else {
        body = caregiverDetailBody(menu);
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

