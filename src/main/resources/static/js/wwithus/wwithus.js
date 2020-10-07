

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
let userType;
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
	userType=document.querySelector("#userType").value;
	urlToRequestNext = document.querySelector("#request-next-url").value;

	removeChildren(balloonsAreaElement);
	loadHistory();
}
//이미지 클릭시 원본 크기로 팝업 보기
function popupImage(url){
	var img = new Image();
	var scWidth = screen.availWidth; //현재 사용중인 스크린 크기를 구함
	var scHeight = screen.availHeight;
	var left = (parseInt(scWidth)-650)/2; //팝업창 위치 조절
	var top = (parseInt(scHeight)-900)/2;
	img.src = url;
	var img_width = img.width-500; //팝업창 크기 조절
	var win_width = img.width-500;
	var height = scHeight;
	var openImage = window.open('','_blank','width='+img_width+',height='+height+',top='+top+',left='+left+',menubars=no,scrollbars=auto');
	openImage.document.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=2, minimum-scale=1, user-scalable=yes,target-densitydpi=medium-dpi\"/><style>body{margin:0px;background-color: black}</style><div style='height:100vh;  display: table-cell; vertical-align: middle'><img style='width: 100%'  src = '"+url+"'width='"+win_width+"' ></div>");
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
function audio_play_pulse(){
	const myAudio = document.getElementById("myAudio");
	console.log(myAudio);
	if(document.getElementById('p-con').className === "ico-play"){
		myAudio.play();
		document.getElementById('p-con').className = "ico-pause";
	}else if(myAudio.play()){
		myAudio.pause();
		document.getElementById('p-con').className = "ico-play";
	}
}
function getCurrentTime(){
	const myAudio = document.getElementById("myAudio");
	console.log("계산하고있니?");
	console.log(myAudio.duration);
	return myAudio.duration;
}
function renderBalloon(chatBalloon)
{
	const div = document.createElement("div");
	div.id = chatBalloon.sequence.toString();
	div.className = "chat-wrap manager";

	const input = `<input type="hidden" value="${chatBalloon.sequence}">`;
	/*th:onclick="'doImgPop(' + ${${chatBalloon.urlToImageFile}} + ')'"*/
	let img = "";
	if (chatBalloon.urlToImageFile)
	{
		<!--href="#"-->
		img = `<div class ="img-wrap>" style="width: 100%"><a class="img-thumb" style="width: 100%"> <span class="img-name"></span> <img style="width: 100%" src="${chatBalloon.urlToImageFile}" 
onclick="popupImage('${chatBalloon.urlToImageFile}')"
alt="이미지가 표시되지 않고 있습니다."></a></div>`
	}


	let audio = "";
	let audio2 = "";
	if (chatBalloon.urlToAudioFile) {
		audio = `<div class="player-wrap">
<audio src="${chatBalloon.urlToAudioFile}" controls ></audio>
</div>`


	}
	let answerButtonsSpan = "";
	if (chatBalloon.answerButtons && chatBalloon.answerButtons.length > 0)
	{
		var positive = "예";
		answerButtonsSpan = renderButtons(chatBalloon);
		console.log(answerButtonsSpan)

	}

	const contentSpan = `<div class ="name-wrap"><span class="ico-withus"></span>
		<span class="  name">  위더스</span></div>
		<div class ="msg-wrap">`
		+audio+
		`<div class="text-wrap">${chatBalloon.content}</div>
		<div class = "btn-wrap grid-col">`
		+answerButtonsSpan+ `</div>` +
		img+`
		</div>`;
	//TODO: 나중에 날짜가 필요하다 하면 이걸 쓰면
	const dateTimeSpan = `<span class="date-time">${chatBalloon.dateString}</span>`;

	if (chatBalloon.direction === "RIGHT")
	{
		div.classList.add("right");
	}

	div.innerHTML = (input /*+ audio*/ + contentSpan /*+ img*/ /*+ answerButtonsSpan*/ /*+ dateTimeSpan*/);

	balloonsAreaElement.appendChild(div);
	window.scrollTo(0, document.body.scrollHeight);
	console.log(userType);
	if (chatBalloon.toScheduleForNextChatBalloon && userType === 'PATIENT' )
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
	div.innerHTML = `<div class="chat-wrap client"><div class="msg-wrap"><div class="txt-wrap">${answerButtonContent}</div></div></div>`;

	balloonsAreaElement.appendChild(div);
}

/**
 * @param {ChatBalloon} chatBalloon
 * @returns {string}
 */
function renderButtons(chatBalloon)
{
	var positive ='';
	let answerButtonsSpan = `<span class="answer-buttons">`;
	let checkButtonType ='';
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
			checkButtonType = `${answerButton.content}`;
			console.log(checkButtonType);
			positive = "예";
			let help = "[위더스 도우미]";
			let negative = "아니요";
			let replacedHref = href.replaceAll(/ {2,}/g, ' ');
			if(checkButtonType.indexOf(positive) === 0){
			/*answerButtonsSpan += `<button class ="btn md type11" onclick="" href="${replacedHref}">`;*/
			answerButtonsSpan += `<button class ="btn md type11" th:onclick="'location.href=\''+ ${replacedHref} +'\';'">`;
				/*answerButtonsSpan += `<button class ="btn md type11" th:onclick="|location.href='${replacedHref}'|">`;*/
			}else if(checkButtonType.indexOf(help) === 0){
				answerButtonsSpan += `<button class ="btn md type10" href="${replacedHref}">`;
			}
			else if(checkButtonType.indexOf(negative) === 0){
				answerButtonsSpan += `<button class ="btn md type09" href='${replacedHref}'">`;
			}
			answerButtonsSpan += `<a style='color: white' href="${replacedHref}">`;
		} else {
			answerButtonsSpan += "<a><button  id = 'checkType2' class = 'btn md type09'>";
		}
		answerButtonsSpan += `${answerButton.content}</a></button><br>`;
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
