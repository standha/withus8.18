class ChatBalloon {
	/**
	 * @param {number} sequence
	 * @param {string} direction
	 * @param {boolean} isToTerminate
	 * @param {boolean} isHelpRequest
	 * @param {boolean} isAnswerExpected
	 * @param {string} content
	 * @param {string | null} urlToImageFile
	 * @param {string | null} urlToAudioFile
	 * @param {number[]} dateTime
	 * @param {string | null} nextCode
	 * @param {AnswerButton[]} answerButtons
	 */
	constructor(sequence, direction, isToTerminate, isHelpRequest, isAnswerExpected, content, urlToImageFile, urlToAudioFile, dateTime, nextCode, answerButtons) {
		if (sequence) {
			this._sequence = sequence;
		} else {
			this._sequence = ChatBalloon.creationCount;
		}

		this._direction = direction;
		this._isToTerminate = isToTerminate;
		this._isHelpRequest = isHelpRequest;
		this._isAnswerExpected = isAnswerExpected;
		this._content = content;
		this._urlToImageFile = urlToImageFile;
		this._urlToAudioFile = urlToAudioFile;
		this._dateTime = dateTime;
		this._nextCode = nextCode;
		this._answerButtons = answerButtons;

		ChatBalloon.creationCount++
	}

	static creationCount = 0;

	/**
	 * @returns {number}
	 */
	get sequence() { return this._sequence; }
	/**
	 * @returns {string}
	 */
	get direction() { return this._direction; }
	/**
	 * @returns {boolean}
	 */
	get isToTerminate() { return this._isToTerminate; }
	/**
	 * @returns {boolean}
	 */
	get isHelpRequest() { return this._isHelpRequest; }
	/**
	 * @returns {boolean}
	 */
	get isAnswerExpected() { return this._isAnswerExpected; }
	/**
	 * @returns {string}
	 */
	get content() { return this._content; }
	/**
	 * @returns {string | null}
	 */
	get urlToImageFile() { return this._urlToImageFile; }
	/**
	 * @returns {string | null}
	 */
	get urlToAudioFile() { return this._urlToAudioFile; }
	/**
	 * @returns {number[]}
	 */
	get dateTime() { return this._dateTime; }
	/**
	 * @returns {string | null}
	 */
	get nextCode() { return this._nextCode; }
	/**
	 * @returns {AnswerButton[]}
	 */
	get answerButtons() { return this._answerButtons; }

	/**
	 * @returns {Date}
	 */
	get date() {
		const year = this._dateTime[0];
		// You suck, javascript.
		const month = (this._dateTime[1] - 1);
		const date = this._dateTime[2];
		const hour = this._dateTime[3];
		const minute = this._dateTime[4];
		const second = this._dateTime[5];

		return new Date(year, month, date, hour, minute, second);
	}

	/**
	 * @returns {string}
	 */
	get dateString() {
		const date = this.date;

		return (date.toDateString() + "\n" + date.toTimeString());
	}

	/**
	 * @returns {string}
	 */
	toJson() {
		const object = {
			sequence: this._sequence,
			direction: this._direction,
			isToTerminate: this._isToTerminate,
			isHelpRequest: this._isHelpRequest,
			isAnswerExpected: this._isAnswerExpected,
			content: this._content,
			urlToImageFile: this._urlToImageFile,
			urlToAudioFile: this._urlToAudioFile,
			dateTime: this._dateTime,
			nextCode: this._nextCode,
			answerButtons: this._answerButtons
		};

		return JSON.stringify(object);
	}

	/**
	 * @param {string} json
	 * @returns {ChatBalloon}
	 */
	static fromJson(json) {
		const errorMessage = ('JSON "' + json + '" is malformed for class "' + ChatBalloon.name + '".');

		let object = JSON.parse(json);
		if (!object) {
			throw new Error(errorMessage);
		}

		return this.fromObject(object);
	}

	/**
	 * @param {Object} object
	 * @returns {ChatBalloon}
	 */
	static fromObject(object) {
		const errorMessage = ('Object "' + object + '" is malformed for class "' + ChatBalloon.name + '".');

		if (
			!object.hasOwnProperty("direction") ||
			!object.hasOwnProperty("toTerminate") ||
			!object.hasOwnProperty("helpRequest") ||
			!object.hasOwnProperty("answerExpected") ||
			!object.hasOwnProperty("content") ||
			!object.hasOwnProperty("dateTime") ||
			!object.hasOwnProperty("answerButtons")
		) {
			throw new Error(errorMessage);
		}

		const sequence = object["sequence"];
		const direction = object["direction"];
		// WARNING: isToTerminate가 아니라 toTerminate인 점에 유의할 것!
		const isToTerminate = object["toTerminate"];
		const isHelpRequest = object["helpRequest"];
		const isAnswerExpected = object["answerExpected"];
		const content = object["content"];
		const urlToImageFile = object["urlToImageFile"];
		const urlToAudioFile = object["urlToAudioFile"];
		const dateTime = object["dateTime"];
		const nextCode = object["nextCode"];
		const answerButtons = object["answerButtons"];

		return new ChatBalloon(sequence, direction, isToTerminate, isHelpRequest, isAnswerExpected, content, urlToImageFile, urlToAudioFile, dateTime, nextCode, answerButtons);
	}
}
