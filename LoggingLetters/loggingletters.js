var sentence = "I love learning software development";

for(var i=0;i<sentence.length; i++){
  if(sentence[i]!== " "){
    console.log(sentence[i])
  }
}

var n = 25;
var result = 0;

if(n%2 !== 0){
  result=-n;
  console.log(result);
}if ( n%2 === 0){
  result=+10;
  console.log(result);
}

var n = 25;
var sum = 0;

for(n = 25; n> 0; n--){
    sum+=n;
    // console.log(sum);
}
