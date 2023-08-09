let moodType=0;
//
// function onFormSubmission(form) {
//     console.log(form)
//     console.log("moodType:" + moodType);
//
//     const body = {
//         moodType: moodType
//     }
//     const url = form.action;
//     const options = {
//         method: "POST",
//         headers: {
//             "Accept": "application/json",
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify(body)
//     };
//     fetch(url, options)
//         .then(response => response.json())
//         .then(data => console.log(data));
//
//     window.location.reload();
//     return false;
// }


function changeToMood(div) {
    const moodBox = document.getElementById(div.id);
    switch (div.id) {
        case"1":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("sad-chk");
                moodBox.textContent = "";
                moodType=1;

            } else if (moodBox.classList.contains('sad-chk')) {
                moodBox.classList.remove("sad-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "슬픔";
                moodType=0;
            }
            break;

        case"2":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("anger-chk");
                moodBox.textContent = "";
                moodType=2;

            } else if (moodBox.classList.contains('anger-chk')) {
                moodBox.classList.remove("anger-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "분노";
                moodType=0;
            }
            break;


        case"3":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("happy-chk");
                moodBox.textContent = "";
                moodType=3;

            } else if (moodBox.classList.contains('happy-chk')) {
                moodBox.classList.remove("happy-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "기쁨";
                moodType=0;
            }
            break;

        case"4":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("surprise-chk");
                moodBox.textContent = "";
                moodType=4;

            } else if (moodBox.classList.contains('surprise-chk')) {
                moodBox.classList.remove("surprise-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "놀람";
                moodType=0;
            }
            break;


        case"5":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("horror-chk");
                moodBox.textContent = "";
                moodType=5;

            } else if (moodBox.classList.contains('horror-chk')) {
                moodBox.classList.remove("horror-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "공포";
                moodType=0;
            }
            break;


        case"6":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("aversion-chk");
                moodBox.textContent = "";
                moodType=6;

            } else if (moodBox.classList.contains('aversion-chk')) {
                moodBox.classList.remove("aversion-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "혐오";
                moodType=0;
            }
            break;

        case"7":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("boring-chk");
                moodBox.textContent = "";
                moodType=7;

            } else if (moodBox.classList.contains('boring-chk')) {
                moodBox.classList.remove("boring-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "지루함";
                moodType=0;
            }
            break;

        case"8":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("interest-chk");
                moodBox.textContent = "";
                moodType=8;

            } else if (moodBox.classList.contains('interest-chk')) {
                moodBox.classList.remove("interest-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "흥미";
                moodType=0;
            }
            break;

        case"9":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("ache-chk");
                moodBox.textContent = "";
                moodType=9;

            } else if (moodBox.classList.contains('ache-chk')) {
                moodBox.classList.remove("ache-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "통증";
                moodType=0;
            }
            break;

    }
    moodType=moodType;
}

function showHistory() {
    const element = document.querySelector("#mindhealth-history-get-action");
    location.href = element.value;
}



