const MESSAGE_INTERVAL_MILLIS = 4444;
const FETCH_OPTIONS = {
	method: "GET",
	headers: {
		"Accept": "application/json",
		"Content-Type": "application/json"
	}
};

document.addEventListener('DOMContentLoaded', function() {
	onDomLoad();
}, false);

function onDomLoad() {
	const url = document.querySelector("#chat-balloon-url").value;

	fetch(url, FETCH_OPTIONS)
		.then(response => response.json())
		.then(object => {
			console.log(object);

			if (object) {
				const data = object["data"];
				if (data) {
					if (Array.isArray(data)) {
						renderBalloons(data);
					} else {
						console.error("Expected an array but WTF is this?", data);
					}
				}
			}
		});
}

function renderBalloons(objects) {
	const parentElement = document.querySelector("div#body");

	objects.forEach(object => {
		const chatBalloon = ChatBalloon.fromObject(object);

		renderBalloon(parentElement, chatBalloon);
		if (!chatBalloon.isAnswerExpected) {
			setInterval(function() {
				requestNext(chatBalloon);
			}, MESSAGE_INTERVAL_MILLIS);
		}
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

	// TODO: specify what to do with '도우미 연결' (chatBalloon.isEmergencyCall)

	const input = '<input type="hidden" value="' + chatBalloon.sequence + '">';
	let audio = "";
	if (chatBalloon.urlToAudioFile) {
		audio = '<audio src="' + chatBalloon.urlToAudioFile + '" controls></audio>';
	}

	const contentSpan =
		'<span class="content">' +
		chatBalloon.content +
		'</span>';
	const dateTimeSpan =
		'<span class="date-time">' +
		chatBalloon.dateString +
		'</span>';

	if (chatBalloon.direction === "RIGHT") {
		div.classList.add("right");
		div.innerHTML = (input + audio + dateTimeSpan + contentSpan);
	} else {
		div.innerHTML = (input + audio + contentSpan + dateTimeSpan);
	}

	parentElement.appendChild(div);
}

/**
 * @param {ChatBalloon} chatBalloon
 */
function requestNext(chatBalloon) {
	const nextCode = chatBalloon.nextCode;

	const parameters = { nextCode: nextCode };
	const url = (document.querySelector("#request-chat-balloon-url").value + new URLSearchParams(parameters));

	fetch(url, FETCH_OPTIONS)
		.then(response => response.json())
		.then(object => {
			console.log(object);

			if (object) {
				const data = object["data"];
				renderBalloons(data);
			}
		});
}
