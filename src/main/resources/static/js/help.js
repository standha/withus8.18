function addHelper() {

    const helper    = parseInt(document.querySelector("input[name=helper]").value);
    const url = document.querySelector("#button-count").value;
    let body = {};
    const alarm    = parseInt(document.querySelector("input[name=alarm]").value);
    const weight  = parseInt(document.querySelector("input[name=weight]").value);
    const bloodPressure = parseInt(document.querySelector("input[name=bloodPressure]").value);
    const goal     = parseInt(document.querySelector("input[name=goal]").value);
    const diseaseInfo   = parseInt(document.querySelector("input[name=diseaseInfo]").value);
    const level    = parseInt(document.querySelector("input[name=level]").value);
    const exercise = parseInt(document.querySelector("input[name=exercise]").value);
    const withusRang = parseInt(document.querySelector("input[name=withusRang]").value);
    const board = parseInt(document.querySelector("input[name=board]").value);
    const mindHealth = parseInt(document.querySelector("input[name=mindHealth]").value);
    const medicine = parseInt(document.querySelector("input[name=medicine]").value);
    const infoEdit = parseInt(document.querySelector("input[name=infoEdit]").value);
    if(url ==="/patient-main-button-count"){
        const symptom    = parseInt(document.querySelector("input[name=symptom]").value);
        const natriumMoisture   = parseInt(document .querySelector("input[name=natriumMoisture]").value);
        body = {
            helper: helper +1,
            symptom: symptom,
            alarm: alarm,
            weight: weight,
            bloodPressure: bloodPressure,
            goal: goal,
            diseaseInfo: diseaseInfo,
            level: level,
            exercise: exercise,
            natriumMoisture: natriumMoisture,
            withusRang: withusRang,
            board: board,
            mindHealth:mindHealth,
            medicine:medicine,
            infoEdit:infoEdit
        };
    } else{
        const familyObservation = parseInt(document.querySelector("input[name=familyObservation]").value);
        const dietManagement = parseInt(document.querySelector("input[name=dietManagement]").value);
        body = {
            helper: helper +1,
            dietManagement: dietManagement,
            alarm: alarm,
            weight: weight,
            bloodPressure: bloodPressure,
            goal: goal,
            diseaseInfo: diseaseInfo,
            level: level,
            exercise: exercise,
            familyObservation: familyObservation,
            withusRang: withusRang,
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
        .then(data => {
            console.log(data);
            document.querySelector("input[name=helper]").value = parseInt(document.querySelector("input[name=helper]").value) + 1;
        })
        .catch(error => {
            console.error('Error:', error);
        });

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