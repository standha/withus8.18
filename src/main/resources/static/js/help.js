function addHelper(count) {
    count = parseInt(count);
    count = count + 1;
    const url = document.querySelector("#button-count").value;
    let body = {};
    const alarm    = document.querySelector("input[name=alarm]").value;
    const weight  = document.querySelector("input[name=weight]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const disease   = document.querySelector("input[name=disease]").value;
    const level    = document.querySelector("input[name=level]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const board = document.querySelector("input[name=board]").value;
    const mindHealth = document.querySelector("input[name=mindHealth]").value;
    const medicine = document.querySelector("input[name=medicine]").value;
    const infoEdit = document.querySelector("input[name=infoEdit]").value;
    if(url ==="/patient-main-button-count"){
        const symptom    = document.querySelector("input[name=symptom]").value;
        const natriumMoisture   = document .querySelector("input[name=natriumMoisture]").value;
        body = {
            helper: count,
            symptom: symptom,
            alarm: alarm,
            weight: weight,
            bloodPressure: blood,
            goal: goal,
            diseaseInfo: disease,
            level: level,
            exercise: exercise,
            natriumMoisture: natriumMoisture,
            withusRang: chat,
            board: board,
            mindHealth:mindHealth,
            medicine:medicine,
            infoEdit:infoEdit
        };
    } else{
        const familyObservation = document.querySelector("input[name=familyObservation]").value;
        const dietManagement = document.querySelector("input[name=dietManagement]").value;
        body = {
            helper: count,
            dietManagement: dietManagement,
            alarm: alarm,
            weight: weight,
            bloodPressure: blood,
            goal: goal,
            diseaseInfo: disease,
            level: level,
            exercise: exercise,
            familyObservation: familyObservation,
            withusRang: chat,
            board: board,
            mindHealth:mindHealth,
            medicine:medicine,
            infoEdit:infoEdit
        };
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

function helperRequest(){
    const url = document.querySelector("#helper-request").value;
    const body = {
        //body 처리 불필요
    }
    const options = {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },body: JSON.stringify(body)
    };
    fetch(url, options)
        .then(response => response.json())
        .then(data => console.log(data));
    return false;
}