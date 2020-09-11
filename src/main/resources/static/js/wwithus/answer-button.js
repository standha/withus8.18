class AnswerButton {
	/**
	 * @param {string} code
	 * @param {number} ordinal
	 * @param {string} content
	 * @param {boolean} isHelpRequest
	 * @param {boolean} isToTerminate
	 * @param {boolean} isToRewind
	 * @param {string | null} urlToImageFile
	 * @param {string | null} nextCode
	 */
	constructor(code, ordinal, isHelpRequest, isToTerminate, isToRewind, content, urlToImageFile, nextCode) {
		this._code = code;
		this._ordinal = ordinal;
		this._isHelpRequest = isHelpRequest;
		this._isToTerminate = isToTerminate;
		this._isToRewind = isToRewind;
		this._content = content;
		this._urlToImageFile = urlToImageFile;
		this._nextCode = nextCode;
	}

	/**
	 * @returns {string}
	 */
	get code() { return this._code; }
	/**
	 * @returns {number}
	 */
	get ordinal() { return this._ordinal; }
	/**
	 * @returns {boolean}
	 */
	get isHelpRequest() { return this._isHelpRequest; }
	/**
	 * @returns {boolean}
	 */
	get isToTerminate() { return this._isToTerminate; }
	/**
	 * @returns {boolean}
	 */
	get isToRewind() { return this._isToRewind; }
	/**
	 * @returns {string}
	 */
	get content() { return this._content; }
	/**
	 * @returns {string|null}
	 */
	get urlToImageFile() { return this._urlToImageFile; }
	/**
	 * @returns {string|null}
	 */
	get nextCode() { return this._nextCode; }

	/**
	 * @returns {string}
	 */
	toJson() {
		const object = {
			code: this._code,
			ordinal: this._ordinal,
			isHelpRequest: this._isHelpRequest,
			isToTerminate: this._isToTerminate,
			isToRewind: this._isToRewind,
			content: this._content,
			urlToImageFile: this._urlToImageFile,
			nextCode: this._nextCode
		};

		return JSON.stringify(object);
	}

	/**
	 * @param {string} json
	 * @returns {AnswerButton}
	 */
	static fromJson(json) {
		const errorMessage = ('JSON "' + json + '" is malformed for class "' + AnswerButton.name + '".');

		let object = JSON.parse(json);
		if (!object) {
			throw new Error(errorMessage);
		}

		return this.fromObject(object);
	}

	/**
	 * @param {Object} object
	 * @returns {AnswerButton}
	 */
	static fromObject(object) {
		const errorMessage = (`Object "${JSON.stringify(object)}" is malformed for class "${AnswerButton.name}".`);

		if (
			!object.hasOwnProperty("code") ||
			!object.hasOwnProperty("ordinal") ||
			!object.hasOwnProperty("helpRequest") ||
			!object.hasOwnProperty("toTerminate") ||
			!object.hasOwnProperty("toRewind") ||
			!object.hasOwnProperty("content")
		) {
			throw new Error(errorMessage);
		}

		const code = object["code"];
		const ordinal = object["ordinal"];
		const isHelpRequest = object["helpRequest"];
		const isToTerminate = object["toTerminate"];
		const isToRewind = object["toRewind"];
		const content = object["content"];
		const urlToImageFile = object["urlToImageFile"];
		const nextCode = object["nextCode"];

		return new AnswerButton(code, ordinal, isHelpRequest, isToTerminate, isToRewind, content, urlToImageFile, nextCode);
	}
}
