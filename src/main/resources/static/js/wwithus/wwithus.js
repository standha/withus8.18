document.addEventListener('DOMContentLoaded', function() {
	onDomLoad();
}, false);

function onDomLoad() {
	const url = document.querySelector("#chat-balloon-url").value;
	const options = {
		method: "GET",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		}
	};

	fetch(url, options)
		.then(response => response.json())
		.then(object => {
			console.log(object);

			let ok = false;
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
	});
}

/**
 *
 * @param {Node} parentElement
 * @param {ChatBalloon} chatBalloon
 */
function renderBalloon(parentElement, chatBalloon) {
	const div = document.createElement("div");
	div.id = chatBalloon.sequence;
	div.className = "balloons";

	const input = '<input type="hidden" value="' + chatBalloon.sequence + '">';
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
		div.innerHTML = (input + "\n" + dateTimeSpan + "\n" + contentSpan);
	} else {
		div.innerHTML = (input + "\n" + contentSpan + "\n" + dateTimeSpan);
	}

	parentElement.appendChild(div);
}
