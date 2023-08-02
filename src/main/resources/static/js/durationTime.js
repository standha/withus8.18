

function start(page) {
    let time = 0;
    window.addEventListener("unload", function() {
        navigator.sendBeacon("/duration_time/"+page, JSON.stringify(time));
    });
    function increaseTime() {
        time += 1;
        console.log("Time:", time);
    }
    setInterval(increaseTime, 60000);


}

function startOneMin(page) {
    function increaseTime() {
        navigator.sendBeacon("/duration_time/"+page, JSON.stringify(1));
    }

    setInterval(increaseTime, 60000);
}

