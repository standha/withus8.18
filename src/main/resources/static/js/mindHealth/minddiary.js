let mood=0;

function onFormSubmission(form) {
    console.log(form)
    console.log(mood)

    const body = {
        mood: mood
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
        .then(data => {
            console.log(data);
        });
    // window.location.reload();
    return false;
}

function toggleButton(buttonId) {
    const button = document.getElementById(buttonId);
    const buttonName = button.getAttribute('name'); // 버튼의 name 속성 값을 가져옴
    const isActive = button.classList.contains(buttonName + "-chk");

    const allButtons = document.querySelectorAll('.mood-box, .' + buttonName + "-chk"); // 모든 버튼과 해당 클래스 버튼 선택
    allButtons.forEach(otherButton => {
        if (otherButton.id !== buttonId) {
            otherButton.disabled = true;
            otherButton.classList.remove(buttonName + "-chk");
            otherButton.classList.add("mood-box");
            otherButton.textContent = getMoodText(otherButton.id);
        }
    });

    if (!isActive) {
        button.disabled = false;
        button.classList.remove("mood-box");
        button.classList.add(buttonName + "-chk");
        button.textContent = "";
        mood = parseInt(buttonId);
    } else {
        allButtons.forEach(otherButton => { // 모든 버튼을 다시 활성화하고 해당 버튼의 클래스를 mood-box로 변경
            otherButton.disabled = false;
            otherButton.classList.remove(otherButton.getAttribute('name') + "-chk");
            otherButton.classList.add("mood-box");
            otherButton.textContent = getMoodText(otherButton.id);
        });
        // moodType = 0;
    }

    console.log("mood: " + mood);
}
function getMoodText(buttonId) {
    switch (buttonId) {
        case "1":
            return "슬픔";
        case "2":
            return "분노";
        case "3":
            return "기쁨";
        case "4":
            return "놀람";
        case "5":
            return "공포";
        case "6":
            return "혐오";
        case "7":
            return "지루함";
        case "8":
            return "흥미";
        case "9":
            return "통증";
        default:
            return "";
    }
}

function showHistory() {
    const element = document.querySelector("#mindhealth-history-get-action");
    location.href = element.value;
}

