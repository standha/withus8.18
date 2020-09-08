class ChatBalloon {
	constructor(sequence, direction, content, dateTime) {
		if (sequence) {
			this._sequence = sequence;
		} else {
			this._sequence = ChatBalloon.creationCount;
		}

		this._direction = direction;
		this._content = content;
		this._dateTime = dateTime;

		ChatBalloon.creationCount++
	}

	static creationCount = 0;

	get sequence() { return this._sequence; }
	get direction() { return this._direction; }
	get content() { return this._content; }
	get dateTime() { return this._dateTime; }
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
	get dateString() {
		const date = this.date;

		return (date.toDateString() + "\n" + date.toTimeString());
	}

	toJson() {
		const object = {
			sequence: this._sequence,
			direction: this._direction,
			content: this._content,
			dateTime: this._dateTime
		};

		return JSON.stringify(object);
	}

	static fromJson(json) {
		const errorMessage = ('JSON "' + json + '" is malformed for class "' + ChatBalloon.name + '".');

		let object = JSON.parse(json);
		if (!object) {
			throw new Error(errorMessage);
		}

		return this.fromObject(object);
	}
	static fromObject(object) {
		const errorMessage = ('Object "' + object + '" is malformed for class "' + ChatBalloon.name + '".');

		if (!object.hasOwnProperty("direction") || !object.hasOwnProperty("content") || !object.hasOwnProperty("dateTime")) {
			throw new Error(errorMessage);
		}

		const sequence = object["sequence"];
		const direction = object["direction"];
		const content = object["content"];
		const dateTime = object["dateTime"];

		return new ChatBalloon(sequence, direction, content, dateTime);
	}
}
