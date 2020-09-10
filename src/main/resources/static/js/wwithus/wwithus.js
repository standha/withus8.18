const MESSAGE_INTERVAL_MILLIS = 2000;
const FETCH_OPTIONS = {
	method: "GET",
	headers: {
		"Accept": "application/json",
		"Content-Type": "application/json"
	}
};

/**
 * @type {Node}
 */
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

	removeChildren(balloonsAreaElement);
	loadHistory();
}

/**
 * @param {Node} parent
 */
function removeChildren(parent) {
	parent.childNodes.forEach(childNode => { parent.removeChild(childNode); });
}

function loadHistory() {
	const url = document.querySelector("#chat-balloon-url").value;

	fetch(url, FETCH_OPTIONS)
		.then(response => response.json())
		.then(object => {
			console.log(object);

			if (object) {
				const data = object["data"];
				if (data && data.length > 0) {
					renderBalloons(data);
				} else {
					requestNext(null);
				}
			}
		});
}

/**
 *
 * @param {Object[]} objects
 */
function renderBalloons(objects) {
	objects.forEach(object => {
		const chatBalloon = ChatBalloon.fromObject(object);

		renderBalloon(balloonsAreaElement, chatBalloon);
	});
}

/**
 *
 * @param {Node} parentElement
 * @param {ChatBalloon} chatBalloon
 */
function renderBalloon(parentElement, chatBalloon) {
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
			if (chatBalloon.isMostRecent) {
				// noinspection JSUnresolvedVariable: 나도 왜 이딴 짓을 해야 하는지 모르겠다.
				if (answerButton.isToTerminate || answerButton.toTerminate) {
					answerButtonsSpan += `<a href="javascript: terminateWwithus();">`;
				} else {
					answerButtonsSpan += `<a href="javascript: requestNextByCode('${answerButton.nextCode}');">`;
				}
			} else {
				answerButtonsSpan += "<a>";
			}

			answerButtonsSpan += `${answerButton.content}</a><br>`;

			if (answerButton.urlToImageFile) {
				answerButtonsSpan += `<img src="${answerButton.urlToImageFile}" alt="이미지가 표시되지 않고 있습니다."><br>`;
			}
		});
		answerButtonsSpan += "</span>";
	}

	if (chatBalloon.direction === "RIGHT") {
		div.classList.add("right");
	}

	div.innerHTML = (input + img + audio + contentSpan + answerButtonsSpan + dateTimeSpan);

	parentElement.appendChild(div);

	if (chatBalloon.isMostRecent && !chatBalloon.isAnswerExpected) {
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
	removeAllAnchorLinks();

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

function terminateWwithus() {
	removeAllAnchorLinks();
}

/**
 * A terrible workaround to remove the href attribute of all anchors within balloonsAreaElement.
 */
function removeAllAnchorLinks() {
	let anchors = balloonsAreaElement.querySelectorAll("a[href]");
	anchors.forEach(anchor => { anchor.removeAttribute("href"); });
}
