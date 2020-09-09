class AnswerButton {
	/**
	 * @param {number} ordinal
	 * @param {string} content
	 * @param {string | null} urlToImageFile
	 * @param {string | null} nextCode
	 */
	constructor(ordinal, content, urlToImageFile, nextCode) {
		this._ordinal = ordinal;
		this._content = content;
		this._urlToImageFile = urlToImageFile;
		this._nextCode = nextCode;
	}

	/**
	 * @returns {number}
	 */
	get ordinal() { return this._ordinal; }
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

		if (!object.hasOwnProperty("ordinal") || !object.hasOwnProperty("content")) {
			throw new Error(errorMessage);
		}

		const ordinal = object["ordinal"];
		// WARNING: isToTerminate가 아니라 toTerminate인 점에 유의할 것!
		const content = object["content"];
		const urlToImageFile = object["urlToImageFile"];
		const nextCode = object["nextCode"];

		return new AnswerButton(ordinal, content, urlToImageFile, nextCode);
	}
}
