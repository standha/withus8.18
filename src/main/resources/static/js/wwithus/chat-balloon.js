class ChatBalloon {
	/**
	 * @param {string} code
	 * @param {number} sequence
	 * @param {string} direction
	 * @param {boolean} isMostRecent
	 * @param {boolean} isAnswerExpected
	 * @param {string} content
	 * @param {string | null} urlToImageFile
	 * @param {string | null} urlToAudioFile
	 * @param {number[]} dateTime
	 * @param {string | null} nextCode
	 * @param {AnswerButton[]} answerButtons
	 * @param {boolean} containsFinishingAnswer
	 */
	constructor(code, sequence, direction, isMostRecent, isAnswerExpected, content, urlToImageFile, urlToAudioFile, dateTime, nextCode, answerButtons, containsFinishingAnswer) {
		this._code = code;

		if (sequence) {
			this._sequence = sequence;
		} else {
			this._sequence = ChatBalloon.creationCount;
		}

		this._direction = direction;
		this._isMostRecent = isMostRecent;
		this._isAnswerExpected = isAnswerExpected;
		this._content = content;
		this._urlToImageFile = urlToImageFile;
		this._urlToAudioFile = urlToAudioFile;
		this._dateTime = dateTime;
		this._nextCode = nextCode;
		this._answerButtons = answerButtons;
		this._containsFinishingAnswer = containsFinishingAnswer;

		ChatBalloon.creationCount++
	}

	static creationCount = 0;

	/**
	 * @returns {string}
	 */
	get code() { return this._code; }
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
	get isMostRecent() { return this._isMostRecent; }
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
	 * @returns {boolean}
	 */
	get containsFinishingAnswer() { return this._containsFinishingAnswer; }

	/**
	 * @returns {Date}
	 */
	get date() {
		const year = this._dateTime[0];
		// You suck, javascript.
		const month = (this._dateTime[1] - 1);
		const date = this._dateTime[2];
		const hour = (this._dateTime[3]? this._dateTime[3]: 0);
		const minute = (this._dateTime[4]? this._dateTime[4]: 0);
		const second = (this._dateTime[5]? this._dateTime[5]: 0);

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
			code: this._code,
			sequence: this._sequence,
			direction: this._direction,
			isMostRecent: this._isMostRecent,
			isAnswerExpected: this._isAnswerExpected,
			content: this._content,
			urlToImageFile: this._urlToImageFile,
			urlToAudioFile: this._urlToAudioFile,
			dateTime: this._dateTime,
			nextCode: this._nextCode,
			answerButtons: this._answerButtons,
			containsFinishingAnswer: this._containsFinishingAnswer
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
		const errorMessage = (`Object "${JSON.stringify(object)}" is malformed for class "${ChatBalloon.name}".`);

		if (
			!object.hasOwnProperty("code") ||
			!object.hasOwnProperty("direction") ||
			!object.hasOwnProperty("mostRecent") ||
			!object.hasOwnProperty("answerExpected") ||
			!object.hasOwnProperty("content") ||
			!object.hasOwnProperty("dateTime") ||
			!object.hasOwnProperty("answerButtons") ||
			!object.hasOwnProperty("containsFinishingAnswer")
		) {
			throw new Error(errorMessage);
		}

		const code = object["code"];
		const sequence = object["sequence"];
		const direction = object["direction"];
		/*
		 * WARNING: isMostRecent가 아니라 mostRecent인 점에 유의할 것!
		 * GSON serialization의 네이밍 규칙에 따른 결과
		 */
		const isMostRecent = object["mostRecent"];
		const isAnswerExpected = object["answerExpected"];
		const content = object["content"];
		const urlToImageFile = object["urlToImageFile"];
		const urlToAudioFile = object["urlToAudioFile"];
		const dateTime = object["dateTime"];
		const nextCode = object["nextCode"];
		const answerButtons = object["answerButtons"];
		const containsFinishingAnswer = object["containsFinishingAnswer"];

		return new ChatBalloon(code, sequence, direction, isMostRecent, isAnswerExpected, content, urlToImageFile, urlToAudioFile, dateTime, nextCode, answerButtons, containsFinishingAnswer);
	}
}
