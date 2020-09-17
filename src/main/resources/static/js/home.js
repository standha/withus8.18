function addSymptom(count) {
    count = parseInt(count);
    count = count + 1;

    const alarm    = document.querySelector("input[name=alarm]").value;
    const blood    = document.querySelector("input[name=blood]").value;
    const disease  = document.querySelector("input[name=disease]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const helper   = document.querySelector("input[name=helper]").value;
    const level    = document.querySelector("input[name=level]").value;
    const namo = document.querySelector("input[name=natriumMoisture]").value;
    const weight   = document .querySelector("input[name=weight]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        symptom: count,
        alarm: alarm,
        bloodPressure: blood,
        diseaseInfo: disease,
        exercise: exercise,
        goal: goal,
        helper: helper,
        level: level,
        natriumMoisture: namo,
        weight: weight,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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

function addAlarm(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const blood    = document.querySelector("input[name=blood]").value;
    const disease  = document.querySelector("input[name=disease]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const helper   = document.querySelector("input[name=helper]").value;
    const level    = document.querySelector("input[name=level]").value;
    const namo = document.querySelector("input[name=natriumMoisture]").value;
    const weight   = document .querySelector("input[name=weight]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        alarm: count,
        symptom: symptom,
        bloodPressure: blood,
        diseaseInfo: disease,
        exercise: exercise,
        goal: goal,
        helper: helper,
        level: level,
        natriumMoisture: namo,
        weight: weight,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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

function addPressure(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const disease  = document.querySelector("input[name=disease]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const helper   = document.querySelector("input[name=helper]").value;
    const level    = document.querySelector("input[name=level]").value;
    const namo = document.querySelector("input[name=natriumMoisture]").value;
    const weight   = document .querySelector("input[name=weight]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        bloodPressure: count,
        symptom: symptom,
        alarm: alarm,
        diseaseInfo: disease,
        exercise: exercise,
        goal: goal,
        helper: helper,
        level: level,
        natriumMoisture: namo,
        weight: weight,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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

function addExercise(count) {
    count = parseInt(count);
    count = count + 1;
    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const disease  = document.querySelector("input[name=disease]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const helper   = document.querySelector("input[name=helper]").value;
    const level    = document.querySelector("input[name=level]").value;
    const namo = document.querySelector("input[name=natriumMoisture]").value;
    const weight   = document .querySelector("input[name=weight]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        exercise: count,
        symptom: symptom,
        alarm: alarm,
        diseaseInfo: disease,
        bloodPressure: blood,
        goal: goal,
        helper: helper,
        level: level,
        natriumMoisture: namo,
        weight: weight,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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

function addMoistureNatrium(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const disease  = document.querySelector("input[name=disease]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const helper   = document.querySelector("input[name=helper]").value;
    const level    = document.querySelector("input[name=level]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const weight   = document .querySelector("input[name=weight]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        natriumMoisture: count,
        symptom: symptom,
        alarm: alarm,
        diseaseInfo: disease,
        bloodPressure: blood,
        goal: goal,
        helper: helper,
        level: level,
        exercise: exercise,
        weight: weight,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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

function addWeight(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const disease  = document.querySelector("input[name=disease]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const helper   = document.querySelector("input[name=helper]").value;
    const level    = document.querySelector("input[name=level]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const natriumMoisture   = document .querySelector("input[name=natriumMoisture]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        weight: count,
        symptom: symptom,
        alarm: alarm,
        diseaseInfo: disease,
        bloodPressure: blood,
        goal: goal,
        helper: helper,
        level: level,
        exercise: exercise,
        natriumMoisture: natriumMoisture,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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

function addDiseaseInfo(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const weight  = document.querySelector("input[name=weight]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const helper   = document.querySelector("input[name=helper]").value;
    const level    = document.querySelector("input[name=level]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const natriumMoisture   = document .querySelector("input[name=natriumMoisture]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        diseaseInfo: count,
        symptom: symptom,
        alarm: alarm,
        weight: weight,
        bloodPressure: blood,
        goal: goal,
        helper: helper,
        level: level,
        exercise: exercise,
        natriumMoisture: natriumMoisture,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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

function addHelper(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const weight  = document.querySelector("input[name=weight]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const goal     = document.querySelector("input[name=goal]").value;
    const disease   = document.querySelector("input[name=disease]").value;
    const level    = document.querySelector("input[name=level]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const natriumMoisture   = document .querySelector("input[name=natriumMoisture]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
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
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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

function addGoal(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const weight  = document.querySelector("input[name=weight]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const helper     = document.querySelector("input[name=helper]").value;
    const disease   = document.querySelector("input[name=disease]").value;
    const level    = document.querySelector("input[name=level]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const natriumMoisture   = document .querySelector("input[name=natriumMoisture]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        goal: count,
        symptom: symptom,
        alarm: alarm,
        weight: weight,
        bloodPressure: blood,
        helper: helper,
        diseaseInfo: disease,
        level: level,
        exercise: exercise,
        natriumMoisture: natriumMoisture,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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

function addChat(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const weight  = document.querySelector("input[name=weight]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const helper     = document.querySelector("input[name=helper]").value;
    const disease   = document.querySelector("input[name=disease]").value;
    const level    = document.querySelector("input[name=level]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const natriumMoisture   = document .querySelector("input[name=natriumMoisture]").value;
    const goal = document.querySelector("input[name=goal]").value;
    const body = {
        withusRang: count,
        symptom: symptom,
        alarm: alarm,
        weight: weight,
        bloodPressure: blood,
        helper: helper,
        diseaseInfo: disease,
        level: level,
        exercise: exercise,
        natriumMoisture: natriumMoisture,
        goal: goal
    };
    const url = document.querySelector("#button-count").value;
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

function addLevel(count) {
    count = parseInt(count);
    count = count + 1;

    const symptom    = document.querySelector("input[name=symptom]").value;
    const alarm    = document.querySelector("input[name=alarm]").value;
    const weight  = document.querySelector("input[name=weight]").value;
    const blood = document.querySelector("input[name=blood]").value;
    const helper     = document.querySelector("input[name=helper]").value;
    const disease   = document.querySelector("input[name=disease]").value;
    const goal    = document.querySelector("input[name=goal]").value;
    const exercise = document.querySelector("input[name=exercise]").value;
    const natriumMoisture   = document .querySelector("input[name=natriumMoisture]").value;
    const chat = document.querySelector("input[name=withusRang]").value;
    const body = {
        level: count,
        symptom: symptom,
        alarm: alarm,
        weight: weight,
        bloodPressure: blood,
        helper: helper,
        diseaseInfo: disease,
        goal: goal,
        exercise: exercise,
        natriumMoisture: natriumMoisture,
        withusRang: chat
    };
    const url = document.querySelector("#button-count").value;
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
