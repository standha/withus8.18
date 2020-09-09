class ChatBalloon {
	constructor(sequence, direction, isEmergencyCall, isAnswerExpected, content, urlToAudioFile, dateTime, nextCode) {
		if (sequence) {
			this._sequence = sequence;
		} else {
			this._sequence = ChatBalloon.creationCount;
		}

		this._direction = direction;
		this._isEmergencyCall = (isEmergencyCall == true);
		this._isAnswerExpected = (isAnswerExpected == true);
		this._content = content;
		this._urlToAudioFile = urlToAudioFile;
		this._dateTime = dateTime;
		this._nextCode = nextCode;

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
	get isEmergencyCall() { return this._isEmergencyCall; }
	/**
	 * @returns {boolean}
	 */
	get isAnswerExpected() { return this._isAnswerExpected; }
	/**
	 * @returns {string}
	 */
	get content() { return this._content; }
	/**
	 * @returns {string}
	 */
	get urlToAudioFile() { return this._urlToAudioFile; }
	/**
	 * @returns {string}
	 */
	get dateTime() { return this._dateTime; }
	/**
	 * @returns {string}
	 */
	get nextCode() {
		return this._nextCode;
	}

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
			isEmergencyCall: this._isEmergencyCall,
			isAnswerExpected: this._isAnswerExpected,
			content: this._content,
			urlToAudioFile: this._urlToAudioFile,
			dateTime: this._dateTime,
			nextCode: this._nextCode
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

		if (!object.hasOwnProperty("direction") || !object.hasOwnProperty("content") || !object.hasOwnProperty("dateTime")) {
			throw new Error(errorMessage);
		}

		const sequence = object["sequence"];
		const direction = object["direction"];
		const isEmergencyCall = object["isEmergencyCall"];
		const isAnswerExpected = object["isAnswerExpected"];
		const content = object["content"];
		const urlToAudioFile = object["urlToAudioFile"];
		const dateTime = object["dateTime"];
		const nextCode = object["nextCode"];

		return new ChatBalloon(sequence, direction, isEmergencyCall, isAnswerExpected, content, urlToAudioFile, dateTime, nextCode);
	}
}
