function animateCounter(id,target,suffix=""){

let element=document.getElementById(id);

let count=0;

let increment=target/100;

let interval=setInterval(function(){

count+=increment;

if(count>=target){
count=target;
clearInterval(interval);
}

element.innerText=Math.floor(count)+suffix;

},20);

}

window.onload=function(){

animateCounter("year",2021);

animateCounter("helped",47,"+");

animateCounter("amount",50,"₹");

animateCounter("community",140,"+");

};