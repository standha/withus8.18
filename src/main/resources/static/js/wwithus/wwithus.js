const MESSAGE_INTERVAL_MILLIS = 2000;
const FETCH_OPTIONS = {
	method: "GET",
	headers: {
		"Accept": "application/json",
		"Content-Type": "application/json"
	}
};

let balloonsAreaElement;
/**
 * @type {string} /wwithus/request-next
 */
let urlToRequestNext;

document.addEventListener("DOMContentLoaded", function() {
	onDomLoad();
}, false);

function onDomLoad() {
	balloonsAreaElement = document.querySelector("div#body");
	urlToRequestNext = document.querySelector("#request-next-url").value;

	loadHistory();
}

function loadHistory() {
	const url = document.querySelector("#chat-balloon-url").value;

	fetch(url, FETCH_OPTIONS)
		.then(response => response.json())
		.then(object => {
			console.log(object);

			if (object) {
				const data = object["data"];
				if (data) {
					if (Array.isArray(data)) {
						renderBalloons(data, false);
					} else {
						console.error("Expected an array but WTF is this?", data);
					}
				} else if (data === null) {
					requestNext(null);
				}
			}
		});
}

/**
 *
 * @param {Object[]} objects
 * @param {boolean} toRequestNext
 */
function renderBalloons(objects, toRequestNext) {
	objects.forEach(object => {
		const chatBalloon = ChatBalloon.fromObject(object);

		renderBalloon(balloonsAreaElement, chatBalloon, toRequestNext);
	});
}

/**
 *
 * @param {Node} parentElement
 * @param {ChatBalloon} chatBalloon
 * @param {boolean} toRequestNext
 */
function renderBalloon(parentElement, chatBalloon, toRequestNext) {
	const div = document.createElement("div");
	div.id = chatBalloon.sequence.toString();
	div.className = "balloons";

	const input = `<input type="hidden" value="${chatBalloon.sequence}">`;

	let img = "";
	if (chatBalloon.urlToImageFile) {
		img = `<img src=${chatBalloon.urlToImageFile}" alt="이미지가 표시되지 않고 있습니다.">`;
	}

	let audio = "";
	if (chatBalloon.urlToAudioFile) {
		audio = `<audio src="${chatBalloon.urlToAudioFile}" controls></audio>`;
	}

	const contentSpan = `<span class="content">${chatBalloon.content}</span>`;
	const dateTimeSpan = `<span class="date-time">${chatBalloon.dateString}</span>`;

	let answerButtonsSpan = "";
	if (chatBalloon.answerButtons && chatBalloon.answerButtons.length > 0) {
		answerButtonsSpan = `<span class="answer-buttons">`;
		chatBalloon.answerButtons.forEach(answerButton => {
			answerButtonsSpan += `<a href="javascript: requestNextByCode('${answerButton.nextCode}')">`;

			answerButtonsSpan += answerButton.content;
			if (answerButton.urlToImageFile) {
				answerButtonsSpan += `<img src="${answerButton.urlToImageFile}" alt="이미지가 표시되지 않고 있습니다.">`;
			}

			answerButtonsSpan += "</a><br>";
		});
		answerButtonsSpan += "</span>";
	}

	if (chatBalloon.direction === "RIGHT") {
		div.classList.add("right");
	}

	div.innerHTML = (input + img + audio + contentSpan + answerButtonsSpan + dateTimeSpan);

	parentElement.appendChild(div);

	if (toRequestNext && !chatBalloon.isAnswerExpected) {
		setTimeout(requestNext, MESSAGE_INTERVAL_MILLIS, chatBalloon);
	}
}

/**
 * @param {ChatBalloon | null} chatBalloon
 */
function requestNext(chatBalloon) {
	const nextCode = (chatBalloon? chatBalloon.nextCode: null);

	requestNextByCode(nextCode);
}

/**
 * @param {string | null} nextCode
 */
function requestNextByCode(nextCode) {
	let modifiedUrlToRequestNext = urlToRequestNext;
	if (nextCode !== null) {
		const parameters = { nextCode: nextCode };

		modifiedUrlToRequestNext += ("?" + new URLSearchParams(parameters));
	}

	fetch(modifiedUrlToRequestNext, FETCH_OPTIONS)
		.then(response => response.json())
		.then(object => {
			console.log(object);

			if (object) {
				const data = object["data"];

				const chatBalloon = ChatBalloon.fromObject(data);
				renderBalloon(balloonsAreaElement, chatBalloon, true);
			}
		});
}
