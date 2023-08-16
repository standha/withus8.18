

/*function start(page) {
    let time = 0;
    window.addEventListener("unload", function() {
        navigator.sendBeacon("/duration_time/"+page, JSON.stringify(time));
    });
    function increaseTime() {
        time += 1;
        console.log("Time:", time);
    }
    setInterval(increaseTime, 10000);


}*/

function start(page) {
    let time = 0;
    function increaseTime() {
        time += 1;
        navigator.sendBeacon("/duration_time/"+page, JSON.stringify(time));
    }

    setInterval(increaseTime, 10000);
}

