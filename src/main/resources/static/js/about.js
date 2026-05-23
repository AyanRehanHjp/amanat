function animateCounter(id,target,suffix=""){

let element=document.getElementById(id);

// Purana counter reset
element.innerText="0";

let count=0;

// Counter speed
let increment=Math.ceil(target/100);

// Counter start
let interval=setInterval(function(){

count+=increment;

// Target stop
if(count>=target){
count=target;
clearInterval(interval);
}

// ₹ format
if(suffix==="₹"){
element.innerText="₹"+count;
}else{
element.innerText=count+suffix;
}

},20);

}

function startCounter(){

// Counters
animateCounter("year",2021);
animateCounter("helped",49,"+");
animateCounter("amount",50,"₹");
animateCounter("community",140,"+");

}

// Page scroll
window.addEventListener("scroll",startCounter);

// Mouse click
window.addEventListener("click",startCounter);

// Mouse move
window.addEventListener("mousemove",startCounter);

// Mobile touch
window.addEventListener("touchstart",startCounter);

// Keyboard press
window.addEventListener("keydown",startCounter);