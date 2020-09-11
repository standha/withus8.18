class AnswerButton {
	/**
	 * @param {number} ordinal
	 * @param {string} content
	 * @param {boolean} isToTerminate
	 * @param {boolean} isToRewind
	 * @param {string | null} urlToImageFile
	 * @param {string | null} nextCode
	 */
	constructor(ordinal, isToTerminate, isToRewind, content, urlToImageFile, nextCode) {
		this._ordinal = ordinal;
		this._isToTerminate = isToTerminate;
		this._isToRewind = isToRewind;
		this._content = content;
		this._urlToImageFile = urlToImageFile;
		this._nextCode = nextCode;
	}

	/**
	 * @returns {number}
	 */
	get ordinal() { return this._ordinal; }
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
			ordinal: this._ordinal,
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
		const errorMessage = ('Object "' + object + '" is malformed for class "' + AnswerButton.name + '".');

		if (!object.hasOwnProperty("ordinal") || !object.hasOwnProperty("toTerminate") || !object.hasOwnProperty("content")) {
			throw new Error(errorMessage);
		}

		const ordinal = object["ordinal"];
		// WARNING: isToTerminate가 아니라 toTerminate인 점에 유의할 것!
		const isToTerminate = object["toTerminate"];
		const isToRewind = object["toRewind"];
		const content = object["content"];
		const urlToImageFile = object["urlToImageFile"];
		const nextCode = object["nextCode"];

		return new AnswerButton(ordinal, isToTerminate, isToRewind, content, urlToImageFile, nextCode);
	}
}
