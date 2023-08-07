// function changeToImage(imagePath) {
//     const moodButton = event.target;
//     moodButton.style.backgroundImage = `url(${imagePath})`;
//     moodButton.innerText = ''; // 버튼 텍스트 지우기
// }

function changeToMood(div) {
    const moodBox = document.getElementById(div.id);
    switch (div.id) {
        case"1":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("sad-chk");
                moodBox.textContent = "";

            } else if (moodBox.classList.contains('sad-chk')) {
                moodBox.classList.remove("sad-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "슬픔";
            }
            break;

        case"2":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("anger-chk");
                moodBox.textContent = "";

            } else if (moodBox.classList.contains('anger-chk')) {
                moodBox.classList.remove("anger-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "분노";
            }
            break;


        case"3":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("happy-chk");
                moodBox.textContent = "";

            } else if (moodBox.classList.contains('happy-chk')) {
                moodBox.classList.remove("happy-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "기쁨";
            }
            break;

        case"4":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("surprise-chk");
                moodBox.textContent = "";

            } else if (moodBox.classList.contains('surprise-chk')) {
                moodBox.classList.remove("surprise-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "놀람";
            }
            break;


        case"5":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("horror-chk");
                moodBox.textContent = "";

            } else if (moodBox.classList.contains('horror-chk')) {
                moodBox.classList.remove("horror-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "공포";
            }
            break;


        case"6":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("aversion-chk");
                moodBox.textContent = "";

            } else if (moodBox.classList.contains('aversion-chk')) {
                moodBox.classList.remove("aversion-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "혐오";
            }
            break;

        case"7":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("boring-chk");
                moodBox.textContent = "";

            } else if (moodBox.classList.contains('boring-chk')) {
                moodBox.classList.remove("boring-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "지루함";
            }
            break;

        case"8":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("interest-chk");
                moodBox.textContent = "";

            } else if (moodBox.classList.contains('interest-chk')) {
                moodBox.classList.remove("interest-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "흥미";
            }
            break;

        case"9":
            if (moodBox.classList.contains('mood-box')) {

                moodBox.classList.remove("mood-box");
                moodBox.classList.add("ache-chk");
                moodBox.textContent = "";

            } else if (moodBox.classList.contains('ache-chk')) {
                moodBox.classList.remove("ache-chk");
                moodBox.classList.add("mood-box");
                moodBox.textContent = "통증";
            }
            break;

            moodBox[i].disabled=false;
    }
}

