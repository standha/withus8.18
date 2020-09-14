/**
 * @param {number} durationInMillisecond
 * @returns {Promise<any>}
 */
function sleep(durationInMillisecond) {
	return new Promise(resolve => setTimeout(resolve, durationInMillisecond));
}
