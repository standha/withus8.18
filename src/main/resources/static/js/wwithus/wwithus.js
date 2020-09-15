const MESSAGE_INTERVAL_MILLIS = 2000;
const GET_FETCH_OPTIONS = {
	method: "GET",
	headers: {
		"Accept": "application/json",
		"Content-Type": "application/json"
	}
};

/**codesToSaveAsHistories
 * @type {Element}
 */
let balloonsAreaElement;

/**
 * @type {string}
 * /wwithus/histories
 */
let urlToHistory;

/**
 * @type {string}
 * /wwithus/request-next
 */
let urlToRequestNext;

document.addEventListener("DOMContentLoaded", function()
{
	onDomLoad();
}, false);


function onDomLoad()
{
	console.log("onDomLoad()");
	balloonsAreaElement = document.querySelector("div#body");
	urlToHistory = document.querySelector("#history-url").value;
	urlToRequestNext = document.querySelector("#request-next-url").value;

	removeChildren(balloonsAreaElement);
	loadHistory();
}

/**
 * @param {Element} parent
 */
function removeChildren(parent)
{
	console.log("removeChildren()");
	parent.childNodes.forEach(childNode => { parent.removeChild(childNode); });
}

function loadHistory()
{
	console.log("loadHistory()");

	fetch(urlToHistory, GET_FETCH_OPTIONS)
		.then(response => response.json())
		.then(object => {
			console.log(object);

			if (object)
			{
				const data = object["data"];
				if (data && data.length > 0)
				{
					renderBalloons(data);
				}
				else
				{
					requestNext(null);
				}
			}
		});
}

function deleteHistory()
{
	const options = {
		method: "DELETE",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		}
	};

	fetch(urlToHistory, options)
		.then(response => response.json())
		.then(object => {
			console.log(object);
		});
}

/**
 *
 * @param {Object[]} objects
 */
function renderBalloons(objects)
{
	objects.forEach(object =>
	{
		const chatBalloon = ChatBalloon.fromObject(object);
		renderBalloon(chatBalloon);
	});
}

/**
 * @param {ChatBalloon} chatBalloon
 */
function renderBalloon(chatBalloon)
{
	const div = document.createElement("div");
	div.id = chatBalloon.sequence.toString();
	div.className = "balloons";

	const input = `<input type="hidden" value="${chatBalloon.sequence}">`;

	let img = "";
	if (chatBalloon.urlToImageFile)
	{
		img = `<img src="${chatBalloon.urlToImageFile}" alt="이미지가 표시되지 않고 있습니다.">`;
	}

	let audio = "";
	if (chatBalloon.urlToAudioFile)
	{
		audio = `<audio src="${chatBalloon.urlToAudioFile}" controls></audio>`;
	}

	const contentSpan = `<span class="content">${chatBalloon.content}</span>`;
	const dateTimeSpan = `<span class="date-time">${chatBalloon.dateString}</span>`;

	let answerButtonsSpan = "";
	if (chatBalloon.answerButtons && chatBalloon.answerButtons.length > 0)
	{
		answerButtonsSpan = renderButtons(chatBalloon);
	}

	if (chatBalloon.direction === "RIGHT")
	{
		div.classList.add("right");
	}

	div.innerHTML = (input + audio + contentSpan + img + answerButtonsSpan + dateTimeSpan);

	balloonsAreaElement.appendChild(div);
	window.scrollTo(0, document.body.scrollHeight);

	if (chatBalloon.toScheduleForNextChatBalloon)
	{
		setTimeout(requestNext, MESSAGE_INTERVAL_MILLIS, chatBalloon);
	}
}

/**
 * @param {string} answerButtonContent
 */
function renderAnswer(answerButtonContent)
{
	const div = document.createElement("div");
	div.className = "answer balloons right";
	div.innerHTML = `<span class="content">${answerButtonContent}</span>`;

	balloonsAreaElement.appendChild(div);
}

/**
 * @param {ChatBalloon} chatBalloon
 * @returns {string}
 */
function renderButtons(chatBalloon)
{
	let answerButtonsSpan = `<span class="answer-buttons">`;

	chatBalloon.answerButtons.forEach(answerButton => {
		if (chatBalloon.isMostRecent) {
			let href = "javascript:";
			/*
			 * 나도 왜 이딴 짓을 해야 하는지 모르겠다.
			 * 아마도 answerButton을 AnswerButton 타입으로 인지하지 못해서?
			 * isToTerminate/isToRewind만으로 충분해야 하지만
			 * 보험 필드 toTerminate/toRewind도 같이 확인 (2020.09.11)
			 */
			if (answerButton.isToRewind || answerButton.toRewind) {
				href += " deleteHistory(); sleep(666).then(() => location.reload());";
			} else {
				if (answerButton.isHelpRequest || answerButton.helpRequest) {
					href += " sendHelpRequest();";
				}

				if (answerButton.isToTerminate || answerButton.toTerminate) {
					href += ` answer('${answerButton.code}', null, '${answerButton.content}', '${chatBalloon.code}', true); sleep(666).then(() => history.back());`;
				} else {
					href += ` answer('${answerButton.code}', '${answerButton.nextCode}', '${answerButton.content}', '${chatBalloon.code}', false);`;
				}
			}

			let replacedHref = href.replaceAll(/ {2,}/g, ' ');
			answerButtonsSpan += `<a href="${replacedHref}">`;
		} else {
			answerButtonsSpan += "<a>";
		}

		answerButtonsSpan += `${answerButton.content}</a><br>`;

		if (answerButton.urlToImageFile) {
			answerButtonsSpan += `<img src="${answerButton.urlToImageFile}" alt="이미지가 표시되지 않고 있습니다."><br>`;
		}
	});

	answerButtonsSpan += "</span>";

	return answerButtonsSpan;
}

/**
 * @param {ChatBalloon | null} chatBalloon
 */
function requestNext(chatBalloon) {
	const currentCode = (chatBalloon? chatBalloon.code: null);
	const nextCode = (chatBalloon? chatBalloon.nextCode: null);

	requestNextByCode(false, currentCode, nextCode);
}

/**
 * @param {boolean} toSkipRendering
 * @param {string} currentCode
 * @param {string | null} nextCode
 * @param {...string} codesToSaveAsHistories
 */
function requestNextByCode(toSkipRendering, currentCode, nextCode, ...codesToSaveAsHistories) {
	removeAllAnchorLinks();

	const body = {
		currentCode: currentCode,
		nextCode: nextCode,
		codesToSaveAsHistories: codesToSaveAsHistories
	};
	const options = {
		method: "POST",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		},
		body: JSON.stringify(body)
	};

	fetch(urlToRequestNext, options)
		.then(response => response.json())
		.then(object => {
			console.log(object);

			if (object) {
				const data = object["data"];

				if (!toSkipRendering) {
					const chatBalloon = ChatBalloon.fromObject(data);
					renderBalloon(chatBalloon);
				}
			}
		});
}

/**
 * @param {string} answerButtonCode
 * @param {string} answerButtonNextCode
 * @param {string} answerButtonContent
 * @param {string} chatBalloonCode
 * @param {boolean} toSkipRendering 실제 UI 업데이트를 무시할지 여부
 */
function answer(answerButtonCode, answerButtonNextCode, answerButtonContent, chatBalloonCode, toSkipRendering) {
	requestNextByCode(toSkipRendering, answerButtonCode, answerButtonNextCode, chatBalloonCode);

	if (!toSkipRendering) {
		renderAnswer(answerButtonContent);
	}
}

/**
 * A terrible workaround to remove the href attribute of all anchors within balloonsAreaElement.
 */
function removeAllAnchorLinks() {
	let anchors = balloonsAreaElement.querySelectorAll("a[href]");
	anchors.forEach(anchor => { anchor.removeAttribute("href"); });
}
